package emSeco.brokerUnit.core.modules.clientComplianceRulesChecker.interfaces;

import emSeco.brokerUnit.core.entities.order.InstitutionalOrder;
import emSeco.brokerUnit.core.entities.order.RetailOrder;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;


import java.util.List;

public interface IClientComplianceRulesChecker {
    List<BooleanResultMessage> checkRetailClientComplianceRules(RetailOrder retailOrder);
    List<BooleanResultMessage> checkInstitutionalClientComplianceRules(InstitutionalOrder institutionalOrder);
}
