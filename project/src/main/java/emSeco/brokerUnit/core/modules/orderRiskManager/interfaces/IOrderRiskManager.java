package emSeco.brokerUnit.core.modules.orderRiskManager.interfaces;

import emSeco.brokerUnit.core.entities.order.InstitutionalOrder;
import emSeco.brokerUnit.core.entities.order.RetailOrder;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;


import java.util.List;

public interface IOrderRiskManager {
    List<BooleanResultMessage> ManageRetailOrderRisks(RetailOrder retailOrder);
    List<BooleanResultMessage> ManageInstitutionalOrderRisks(InstitutionalOrder institutionalOrder);
}
