package emSeco.brokerUnit.core.modules.orderRisks.retailOrderRisks.interfaces;

import emSeco.brokerUnit.core.entities.order.RetailOrder;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;


public interface IRetailOrderRisk {
    BooleanResultMessage ManageRisk(RetailOrder RetailOrder);
}
