package emSeco.exchangeUnit.core.modules.orderValidationRules.sharedOrderValidationRules.interfaces;

import emSeco.exchangeUnit.core.entities.order.Order;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;


public interface ISharedOrderValidationRule {
    BooleanResultMessage checkRule(Order order);
}
