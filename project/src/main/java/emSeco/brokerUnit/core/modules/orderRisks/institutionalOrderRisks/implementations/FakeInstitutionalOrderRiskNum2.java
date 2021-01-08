package emSeco.brokerUnit.core.modules.orderRisks.institutionalOrderRisks.implementations;

//#if FakeInstitutionalOrderRiskNum2
import emSeco.brokerUnit.core.entities.order.InstitutionalOrder;
import emSeco.brokerUnit.core.modules.orderRisks.institutionalOrderRisks.interfaces.IInstitutionalOrderRisk;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;

public class FakeInstitutionalOrderRiskNum2 implements IInstitutionalOrderRisk {
    @Override
    public BooleanResultMessage ManageRisk(InstitutionalOrder institutionalOrder) {
        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }
}
//#endif