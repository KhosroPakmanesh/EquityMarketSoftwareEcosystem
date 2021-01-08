package emSeco.brokerUnit.core.modules.governmentalComplianceRules.institutionalGovernmentalComplianceRules.implementations;

//#if InstitutionalWashSaleRule
import emSeco.brokerUnit.core.entities.order.InstitutionalOrder;
import emSeco.brokerUnit.core.modules.governmentalComplianceRules.institutionalGovernmentalComplianceRules.interfaces.IInstitutionalGovernmentalComplianceRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;

public class InstitutionalWashSaleRule implements IInstitutionalGovernmentalComplianceRule {
    @Override
    public BooleanResultMessage checkRule(InstitutionalOrder institutionalOrder) {
        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }
}
//#endif