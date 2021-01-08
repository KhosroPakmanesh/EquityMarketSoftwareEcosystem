package emSeco.brokerUnit.core.modules.orderValidationRules.retailOrderValidationRules.interfaces;

import emSeco.brokerUnit.core.entities.order.RetailOrder;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;


public interface IRetailOrderValidationRule {
    BooleanResultMessage checkRule(RetailOrder RetailOrder);
}
