package emSeco.custodianUnit.core.modules.custodianEquityTransferMethods.implementations;

//#if CustodianInternalDematAccountMoneyTransferMethod
import com.google.inject.Inject;
import emSeco.custodianUnit.core.entities.shared.EquityTransferMethod;
import emSeco.custodianUnit.core.entities.custodianDematAccount.CustodianDematAccount;
import emSeco.custodianUnit.core.modules.custodianEquityTransferMethods.interfaces.ICustodianEquityTransferMethod;
import emSeco.custodianUnit.core.services.infrastructureServices.databases.custodianUnitRepositories.interfaces.ICustodianUnitRepositories;
import emSeco.custodianUnit.core.services.infrastructureServices.gateways.custodianUnitApiGateway.interfaces.gateways.ICustodianUnitApiGateways;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;


import java.util.UUID;

public class CustodianInternalDematAccountMoneyTransferMethod implements ICustodianEquityTransferMethod {
    private final ICustodianUnitRepositories custodianUnitRepositories;
    private final ICustodianUnitApiGateways custodianUnitApiGateways;

    @Inject
    public CustodianInternalDematAccountMoneyTransferMethod(ICustodianUnitRepositories custodianUnitRepositories,
                                                            ICustodianUnitApiGateways custodianUnitApiGateways) {
        this.custodianUnitRepositories = custodianUnitRepositories;
        this.custodianUnitApiGateways = custodianUnitApiGateways;
    }

    @Override
    public EquityTransferMethod getEquityTransferMethod() {
        return EquityTransferMethod.custodianInternalAccount;
    }

    @Override
    public BooleanResultMessage transferFromClientToCustodian(
            UUID custodianDepositoryId, UUID custodianDematAccountNumber,
            UUID clientDepositoryId, UUID clientDematAccountNumber,
            UUID custodianInternalDematAccountNumber, UUID clientInternalDematAccountNumber,
            String instrumentName, int quantity
    ) {

        CustodianDematAccount clientCustodianematAccount = custodianUnitRepositories.
                getCustodianDematAccountRepository().get(clientInternalDematAccountNumber);

        if (clientCustodianematAccount == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("Client's custodian demat accounts was not found!"));
        }

        Boolean hasEnoughBalance = clientCustodianematAccount.
                HasEnoughBalance(instrumentName, quantity);
        if (!hasEnoughBalance) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The client does not have enough balance!"));
        }

        clientCustodianematAccount.debit(instrumentName, quantity);
        BooleanResultMessage creditAccountResultMessage =
                custodianUnitApiGateways.getCustodianToDepositoryUnitApiGateway().
                        credit(custodianDepositoryId, custodianDematAccountNumber,
                                instrumentName, quantity);

        if (!creditAccountResultMessage.getOperationResult()) {
            return creditAccountResultMessage;
        }

        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }

    @Override
    public BooleanResultMessage transferFromCustodianToClient(
            UUID custodianDepositoryId, UUID custodianDematAccountNumber,
            UUID clientDepositoryId, UUID clientDematAccountNumber,
            UUID custodianInternalDematAccountNumber, UUID clientInternalDematAccountNumber,
            String instrumentName, int quantity) {
        CustodianDematAccount clientCustodianDematAccount = custodianUnitRepositories.
                getCustodianDematAccountRepository().get(clientInternalDematAccountNumber);

        if (clientCustodianDematAccount == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("Client's custodian demat accounts was not found!"));
        }
        BooleanResultMessage debitAccountResultMessage =
                custodianUnitApiGateways.getCustodianToDepositoryUnitApiGateway().
                        debit(custodianDepositoryId, custodianDematAccountNumber,
                                instrumentName, quantity);

        if (!debitAccountResultMessage.getOperationResult()) {
            return debitAccountResultMessage;
        }
        clientCustodianDematAccount.credit(instrumentName, quantity);

        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }
}
//#endif
