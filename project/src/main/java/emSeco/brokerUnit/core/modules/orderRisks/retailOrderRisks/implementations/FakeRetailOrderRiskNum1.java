package emSeco.brokerUnit.core.modules.orderRisks.retailOrderRisks.implementations;

//#if FakeRetailOrderRiskNum1
import emSeco.brokerUnit.core.entities.order.RetailOrder;
import emSeco.brokerUnit.core.modules.orderRisks.retailOrderRisks.interfaces.IRetailOrderRisk;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;

public class FakeRetailOrderRiskNum1 implements IRetailOrderRisk {
    @Override
    public BooleanResultMessage ManageRisk(RetailOrder RetailOrder) {
        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }
}
//#endif