package emSeco.custodianUnit.core.modules.custodianEquityTransferMethods.implementations;

//#if CustodianDepositoryDematAccountEquityTransferMethod
import com.google.inject.Inject;
import emSeco.custodianUnit.core.entities.shared.EquityTransferMethod;
import emSeco.custodianUnit.core.modules.custodianEquityTransferMethods.interfaces.ICustodianEquityTransferMethod;
import emSeco.custodianUnit.core.services.infrastructureServices.gateways.custodianUnitApiGateway.interfaces.gateways.ICustodianUnitApiGateways;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;


import java.util.UUID;

public class DepositoryDematAccountEquityTransferMethod implements ICustodianEquityTransferMethod {

    private final ICustodianUnitApiGateways custodianUnitApiGateways;

    @Inject
    public DepositoryDematAccountEquityTransferMethod(ICustodianUnitApiGateways custodianUnitApiGateways) {
        this.custodianUnitApiGateways = custodianUnitApiGateways;
    }

    @Override
    public EquityTransferMethod getEquityTransferMethod() {
        return EquityTransferMethod.depositoryDematAccount;
    }

    @Override
    public BooleanResultMessage transferFromClientToCustodian(
            UUID custodianDepositoryId, UUID custodianDematAccountNumber,
            UUID clientDepositoryId, UUID clientDematAccountNumber,
            UUID custodianInternalDematAccountNumber, UUID clientInternalDematAccountNumber,
            String instrumentName, int quantity) {

        BooleanResultMessage debitAccountResultMessage =
                custodianUnitApiGateways.getCustodianToDepositoryUnitApiGateway().
                        debit(clientDepositoryId, clientDematAccountNumber,
                                instrumentName, quantity);

        if (!debitAccountResultMessage.getOperationResult()) {
            return debitAccountResultMessage;
        }

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
            String instrumentName, int quantity
    ) {
        BooleanResultMessage debitAccountResultMessage =
                custodianUnitApiGateways.getCustodianToDepositoryUnitApiGateway().
                        debit(custodianDepositoryId, custodianDematAccountNumber,
                                instrumentName, quantity);

        if (!debitAccountResultMessage.getOperationResult()) {
            return debitAccountResultMessage;
        }

        BooleanResultMessage creditAccountResultMessage =
                custodianUnitApiGateways.getCustodianToDepositoryUnitApiGateway().
                        credit(clientDepositoryId,
                                clientDematAccountNumber,
                                instrumentName,
                                quantity);

        if (!creditAccountResultMessage.getOperationResult()) {
            return creditAccountResultMessage;
        }

        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }
}
//#endif
