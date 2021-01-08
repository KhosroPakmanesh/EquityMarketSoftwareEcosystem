package emSeco.brokerUnit.core.modules.orderValidationRules.retailOrderValidationRules.implementations;

//#if BrokerRetailOrderAccountsInformationValidation
import emSeco.brokerUnit.core.entities.order.RetailOrder;
import emSeco.brokerUnit.core.modules.orderValidationRules.retailOrderValidationRules.interfaces.IRetailOrderValidationRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;

public class RetailOrderAccountsInformationValidation implements IRetailOrderValidationRule {
    @Override
    public BooleanResultMessage checkRule(RetailOrder RetailOrder) {
        if (RetailOrder.getAccountsInformation().getClearingBankId() == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The clearingBankId field for the retail order is empty!"));
        }
        if (RetailOrder.getAccountsInformation().getClearingBankAccountNumber() == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The clearing bank account number field for the retail order is empty!"));
        }
        if (RetailOrder.getAccountsInformation().getDepositoryId() == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The depositoryId field for the retail order is empty!"));
        }
        if (RetailOrder.getAccountsInformation().getDematAccountNumber() == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The demat account number field for the retail order is empty!"));
        }

        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }
}
//#endif
