package emSeco.brokerUnit.core.modules.orderRisks.sharedOrderRisks.interfaces;

import emSeco.brokerUnit.core.entities.order.Order;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;


public interface ISharedOrderRisk {
    BooleanResultMessage ManageRisk(Order order);
}
