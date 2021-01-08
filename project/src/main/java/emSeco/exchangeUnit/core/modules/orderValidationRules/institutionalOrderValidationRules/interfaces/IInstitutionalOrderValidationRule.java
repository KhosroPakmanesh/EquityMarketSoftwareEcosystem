package emSeco.exchangeUnit.core.modules.orderValidationRules.institutionalOrderValidationRules.interfaces;


import emSeco.exchangeUnit.core.entities.order.Order;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;


public interface IInstitutionalOrderValidationRule {
    BooleanResultMessage checkRule(Order institutionalOrder);
}
