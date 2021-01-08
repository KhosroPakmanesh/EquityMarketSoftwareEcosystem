package emSeco.brokerUnit.core.modules.orderValidationRules.sharedOrderValidationRules.interfaces;

import emSeco.brokerUnit.core.entities.order.Order;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;


public interface ISharedOrderValidationRule {
    BooleanResultMessage checkRule(Order order);
}
