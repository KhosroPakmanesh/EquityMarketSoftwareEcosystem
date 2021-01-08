package emSeco.brokerUnit.core.modules.orderRisks.sharedOrderRisks.implementations;

//#if FakeSharedOrderRiskNum1
import emSeco.brokerUnit.core.entities.order.Order;
import emSeco.brokerUnit.core.modules.orderRisks.sharedOrderRisks.interfaces.ISharedOrderRisk;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;

public class FakeSharedOrderRiskNum1 implements ISharedOrderRisk {
    @Override
    public BooleanResultMessage ManageRisk(Order order) {
        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }
}
//#endif