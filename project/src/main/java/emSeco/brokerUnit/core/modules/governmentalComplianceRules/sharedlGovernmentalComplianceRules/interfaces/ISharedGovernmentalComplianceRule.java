package emSeco.brokerUnit.core.modules.governmentalComplianceRules.sharedlGovernmentalComplianceRules.interfaces;

import emSeco.brokerUnit.core.entities.order.Order;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;


public interface ISharedGovernmentalComplianceRule {
    BooleanResultMessage checkRule(Order order);
}
