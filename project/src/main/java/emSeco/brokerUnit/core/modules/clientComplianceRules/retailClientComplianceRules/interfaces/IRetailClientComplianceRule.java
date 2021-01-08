package emSeco.brokerUnit.core.modules.clientComplianceRules.retailClientComplianceRules.interfaces;

import emSeco.brokerUnit.core.entities.order.RetailOrder;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;


public interface IRetailClientComplianceRule {
    BooleanResultMessage checkRule(RetailOrder retailOrder);
}
