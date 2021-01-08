package emSeco.brokerUnit.core.modules.orderValidationRules.sharedOrderValidationRules.implementations;

//#if BrokerSharedOrderTradingInformationValidation
import emSeco.brokerUnit.core.entities.order.Order;
import emSeco.brokerUnit.core.modules.orderValidationRules.sharedOrderValidationRules.interfaces.ISharedOrderValidationRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;

public class SharedOrderTradingInformationValidation implements ISharedOrderValidationRule {
    @Override
    public BooleanResultMessage checkRule(Order order) {
        if (order.getOrderId() == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The orderId field is empty or its value is invalid!"));
        }

        if (order.getTradingInformation().getOrderInitiationDate() == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The orderInitiationDate field is empty or its value is invalid!"));
        }

        if (order.getTradingInformation().getOrderType() == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The order type field is empty or its value is invalid!"));
        }

        return new BooleanResultMessage
                (true, OperationMessage.emptyOperationMessage());
    }
}
//#endif
