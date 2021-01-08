package emSeco.exchangeUnit.core.modules.orderPrecedenceRules.defaultSecondaryOrderPrecedenceRules.interfaces;

import emSeco.exchangeUnit.core.entities.order.Order;

public interface IDefaultSecondaryOrderPrecedenceRule {
    int compareOrders(Order baseOrder, Order otherOrder);
}
