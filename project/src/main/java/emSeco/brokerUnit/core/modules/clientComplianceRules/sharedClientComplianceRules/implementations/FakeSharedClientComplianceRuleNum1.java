package emSeco.brokerUnit.core.modules.clientComplianceRules.sharedClientComplianceRules.implementations;

import emSeco.brokerUnit.core.entities.order.Order;
import emSeco.brokerUnit.core.modules.clientComplianceRules.sharedClientComplianceRules.interfaces.ISharedClientComplianceRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;


public class FakeSharedClientComplianceRuleNum1 implements ISharedClientComplianceRule {
    @Override
    public BooleanResultMessage checkRule(Order order) {
        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }
}
