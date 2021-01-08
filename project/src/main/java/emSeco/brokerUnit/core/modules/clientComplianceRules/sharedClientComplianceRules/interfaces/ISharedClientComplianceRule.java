package emSeco.brokerUnit.core.modules.clientComplianceRules.sharedClientComplianceRules.interfaces;

import emSeco.brokerUnit.core.entities.order.Order;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;


public interface ISharedClientComplianceRule {
    BooleanResultMessage checkRule(Order order);
}
