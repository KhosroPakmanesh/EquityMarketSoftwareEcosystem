package emSeco.brokerUnit.core.modules.orderValidationRules.sharedOrderValidationRules.implementations;

//#if SharedOrderMoneyEquityTransferMethodValidation
import emSeco.brokerUnit.core.entities.order.Order;
import emSeco.brokerUnit.core.modules.orderValidationRules.sharedOrderValidationRules.interfaces.ISharedOrderValidationRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;

public class SharedOrderMoneyEquityTransferMethodValidation implements ISharedOrderValidationRule {
    @Override
    public BooleanResultMessage checkRule(Order order) {
        if (order.getMoneyTransferMethod() == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The money transfer method field is empty or its value is invalid!"));
        }

        if (order.getEquityTransferMethod() == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The equity transfer method field is empty or its value is invalid!"));
        }

        return new BooleanResultMessage
                (true, OperationMessage.emptyOperationMessage());
    }
}
//#endif
