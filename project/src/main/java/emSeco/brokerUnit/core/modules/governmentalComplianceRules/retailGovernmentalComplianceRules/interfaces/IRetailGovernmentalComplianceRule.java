package emSeco.brokerUnit.core.modules.governmentalComplianceRules.retailGovernmentalComplianceRules.interfaces;

import emSeco.brokerUnit.core.entities.order.RetailOrder;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;

public interface IRetailGovernmentalComplianceRule {
    BooleanResultMessage checkRule(RetailOrder retailOrder);
}
