package emSeco.custodianUnit.core.modules.custodianMoneyTransferMethods.implementations;

//#if CustodianClearingBankAccountMoneyTransferMethod
import com.google.inject.Inject;
import emSeco.custodianUnit.core.entities.shared.MoneyTransferMethod;
import emSeco.custodianUnit.core.modules.custodianMoneyTransferMethods.interfaces.ICustodianMoneyTransferMethod;
import emSeco.custodianUnit.core.services.infrastructureServices.gateways.custodianUnitApiGateway.interfaces.gateways.ICustodianUnitApiGateways;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;


import java.util.UUID;

public class ClearingBankAccountMoneyTransferMethod implements ICustodianMoneyTransferMethod {
    private final ICustodianUnitApiGateways custodianUnitApiGateways;

    @Inject
    public ClearingBankAccountMoneyTransferMethod(ICustodianUnitApiGateways custodianUnitApiGateways) {
        this.custodianUnitApiGateways = custodianUnitApiGateways;
    }

    @Override
    public MoneyTransferMethod getMoneyTransferMethod() {
        return MoneyTransferMethod.clearingBankAccount;
    }

    @Override
    public BooleanResultMessage transferFromClientToCustodian
            (UUID custodianClearingBankId, UUID custodianClearingBankAccountNumber,
             UUID clientClearingBankId, UUID clientClearingBankAccountNumber,
             UUID cusodianinternalBankAccountNumber, UUID clientInternalBankAccountNumber, double totalPrice) {

        BooleanResultMessage debitAccountResultMessage =
                custodianUnitApiGateways.getCustodianToClearingBankUnitApiGateway().
                        debit(clientClearingBankId, clientClearingBankAccountNumber, totalPrice);

        if (!debitAccountResultMessage.getOperationResult()) {
            return debitAccountResultMessage;
        }

        BooleanResultMessage creditAccountResultMessage =
                custodianUnitApiGateways.getCustodianToClearingBankUnitApiGateway().
                        credit(custodianClearingBankId, custodianClearingBankAccountNumber, totalPrice);

        if (!creditAccountResultMessage.getOperationResult()) {
            return creditAccountResultMessage;
        }

        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }

    @Override
    public BooleanResultMessage transferFromCustodianToClient
            (UUID custodianClearingBankId, UUID custodianClearingBankAccountNumber,
             UUID clientClearingBankId, UUID clientClearingBankAccountNumber,
             UUID custodianInternalBankAccountNumber, UUID clientInternalBankAccountNumber,
             double totalPrice) {

        BooleanResultMessage debitAccountResultMessage =
                custodianUnitApiGateways.getCustodianToClearingBankUnitApiGateway().
                        debit(custodianClearingBankId, custodianClearingBankAccountNumber, totalPrice);

        if (!debitAccountResultMessage.getOperationResult()) {
            return debitAccountResultMessage;
        }

        BooleanResultMessage creditAccountResultMessage =
                custodianUnitApiGateways.getCustodianToClearingBankUnitApiGateway().
                        credit(clientClearingBankId, clientClearingBankAccountNumber, totalPrice);

        if (!creditAccountResultMessage.getOperationResult()) {
            return creditAccountResultMessage;
        }

        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }
}
//#endif
