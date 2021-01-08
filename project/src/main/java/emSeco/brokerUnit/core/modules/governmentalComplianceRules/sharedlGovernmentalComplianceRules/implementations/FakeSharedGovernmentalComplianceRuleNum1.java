package emSeco.brokerUnit.core.modules.governmentalComplianceRules.sharedlGovernmentalComplianceRules.implementations;

import emSeco.brokerUnit.core.entities.order.Order;
import emSeco.brokerUnit.core.modules.governmentalComplianceRules.sharedlGovernmentalComplianceRules.interfaces.ISharedGovernmentalComplianceRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;


public class FakeSharedGovernmentalComplianceRuleNum1 implements ISharedGovernmentalComplianceRule {
    @Override
    public BooleanResultMessage checkRule(Order order) {
        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }
}
