package emSeco.brokerUnit.core.modules.clientComplianceRules.retailClientComplianceRules.implementations;

import emSeco.brokerUnit.core.entities.order.RetailOrder;
import emSeco.brokerUnit.core.modules.clientComplianceRules.retailClientComplianceRules.interfaces.IRetailClientComplianceRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;


public class FakeRetailClientComplianceRuleNum2 implements IRetailClientComplianceRule {
    @Override
    public BooleanResultMessage checkRule(RetailOrder retailOrder) {
        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }
}
