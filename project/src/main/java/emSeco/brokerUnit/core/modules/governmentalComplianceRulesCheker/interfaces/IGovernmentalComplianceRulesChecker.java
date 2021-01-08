package emSeco.brokerUnit.core.modules.governmentalComplianceRulesCheker.interfaces;

import emSeco.brokerUnit.core.entities.order.InstitutionalOrder;
import emSeco.brokerUnit.core.entities.order.RetailOrder;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;


import java.util.List;

public interface IGovernmentalComplianceRulesChecker {
    List<BooleanResultMessage> checkRetailGovernmentalComplianceRules(RetailOrder retailOrder);
    List<BooleanResultMessage> checkInstitutionalGovernmentalComplianceRules(InstitutionalOrder institutionalOrder);
}
