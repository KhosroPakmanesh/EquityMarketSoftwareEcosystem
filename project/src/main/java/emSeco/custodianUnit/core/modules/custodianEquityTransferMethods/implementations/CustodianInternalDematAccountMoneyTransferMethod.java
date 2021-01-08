package emSeco.custodianUnit.core.modules.custodianEquityTransferMethods.implementations;

//#if CustodianInternalDematAccountMoneyTransferMethod
import com.google.inject.Inject;
import emSeco.custodianUnit.core.entities.shared.EquityTransferMethod;
import emSeco.custodianUnit.core.entities.custodianDematAccount.CustodianDematAccount;
import emSeco.custodianUnit.core.modules.custodianEquityTransferMethods.interfaces.ICustodianEquityTransferMethod;
import emSeco.custodianUnit.core.services.infrastructureServices.databases.custodianUnitRepositories.interfaces.ICustodianUnitRepositories;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;


import java.util.UUID;

public class CustodianInternalDematAccountMoneyTransferMethod implements ICustodianEquityTransferMethod {
    private final ICustodianUnitRepositories custodianUnitRepositories;

    @Inject
    public CustodianInternalDematAccountMoneyTransferMethod(ICustodianUnitRepositories custodianUnitRepositories) {
        this.custodianUnitRepositories = custodianUnitRepositories;
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
        CustodianDematAccount custodianDematAccount = custodianUnitRepositories.
                getCustodianDematAccountRepository().get(custodianInternalDematAccountNumber);

        CustodianDematAccount clientCustodianematAccount = custodianUnitRepositories.
                getCustodianDematAccountRepository().get(clientInternalDematAccountNumber);

        if (custodianDematAccount == null || clientCustodianematAccount == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("One or both of clearing bank accounts were not found!"));
        }

        Boolean hasEnoughBalance = clientCustodianematAccount.
                HasEnoughBalance(instrumentName, quantity);
        if (!hasEnoughBalance) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The client does not have enough balance!"));
        }

        clientCustodianematAccount.debit(instrumentName, quantity);
        custodianDematAccount.credit(instrumentName, quantity);

        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }

    @Override
    public BooleanResultMessage transferFromCustodianToClient(
            UUID custodianDepositoryId, UUID custodianDematAccountNumber,
            UUID clientDepositoryId, UUID clientDematAccountNumber,
            UUID custodianInternalDematAccountNumber, UUID clientInternalDematAccountNumber,
            String instrumentName, int quantity) {

        CustodianDematAccount custodianDematAccount = custodianUnitRepositories.
                getCustodianDematAccountRepository().get(custodianInternalDematAccountNumber);

        CustodianDematAccount clientCustodianDematAccount = custodianUnitRepositories.
                getCustodianDematAccountRepository().get(clientInternalDematAccountNumber);

        if (custodianDematAccount == null || clientCustodianDematAccount == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("One or both of clearing bank accounts were not found!"));
        }

        Boolean hasEnoughBalance = custodianDematAccount.
                HasEnoughBalance(instrumentName, quantity);
        if (!hasEnoughBalance) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The client does not have enough balance!"));
        }

        custodianDematAccount.debit(instrumentName, quantity);
        clientCustodianDematAccount.credit(instrumentName, quantity);

        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }
}
//#endif
