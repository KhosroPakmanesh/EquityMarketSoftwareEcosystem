package emSeco.exchangeUnit.core.modules.orderPrecedenceRules.secondaryOrderPrecedenceRules.interfaces;

import emSeco.exchangeUnit.core.entities.order.Order;

public interface ISecondaryOrderPrecedenceRule {
    int compareOrders(Order baseOrder, Order otherOrder);
}
