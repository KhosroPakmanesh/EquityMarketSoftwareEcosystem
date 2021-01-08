package emSeco.exchangeUnit.core.modules.orderValidationRules.retailOrderValidationRules.interfaces;

import emSeco.exchangeUnit.core.entities.order.Order;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;

public interface IRetailOrderValidationRule {
    BooleanResultMessage checkRule(Order RetailOrder);
}
