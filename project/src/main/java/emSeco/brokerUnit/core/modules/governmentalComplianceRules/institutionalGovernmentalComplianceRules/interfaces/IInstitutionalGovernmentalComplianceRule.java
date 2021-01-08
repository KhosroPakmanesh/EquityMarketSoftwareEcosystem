package emSeco.brokerUnit.core.modules.governmentalComplianceRules.institutionalGovernmentalComplianceRules.interfaces;

import emSeco.brokerUnit.core.entities.order.InstitutionalOrder;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;


public interface IInstitutionalGovernmentalComplianceRule {
    BooleanResultMessage checkRule(InstitutionalOrder institutionalOrder);
}
