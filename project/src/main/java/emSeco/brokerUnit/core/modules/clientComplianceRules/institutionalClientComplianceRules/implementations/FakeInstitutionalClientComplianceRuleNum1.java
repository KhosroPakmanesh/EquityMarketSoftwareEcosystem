package emSeco.brokerUnit.core.modules.clientComplianceRules.institutionalClientComplianceRules.implementations;

//#if FakeInstitutionalClientComplianceRuleNum1
import emSeco.brokerUnit.core.entities.order.InstitutionalOrder;
import emSeco.brokerUnit.core.modules.clientComplianceRules.institutionalClientComplianceRules.interfaces.IInstitutionalClientComplianceRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;


public class FakeInstitutionalClientComplianceRuleNum1 implements IInstitutionalClientComplianceRule {
    @Override
    public BooleanResultMessage checkRule(InstitutionalOrder institutionalOrder) {
        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }
}
//#endif