package emSeco.brokerUnit.core.modules.clientComplianceRules.institutionalClientComplianceRules.interfaces;

import emSeco.brokerUnit.core.entities.order.InstitutionalOrder;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;


public interface IInstitutionalClientComplianceRule {
    BooleanResultMessage checkRule(InstitutionalOrder institutionalOrder);
}
