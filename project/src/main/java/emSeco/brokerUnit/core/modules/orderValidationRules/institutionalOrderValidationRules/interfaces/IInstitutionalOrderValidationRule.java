package emSeco.brokerUnit.core.modules.orderValidationRules.institutionalOrderValidationRules.interfaces;

import emSeco.brokerUnit.core.entities.order.InstitutionalOrder;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;


public interface IInstitutionalOrderValidationRule {
    BooleanResultMessage checkRule(InstitutionalOrder institutionalOrder);
}
