package emSeco.exchangeUnit.core.modules.orderComparator.interfaces;

import emSeco.exchangeUnit.core.entities.order.Order;

public interface IOrderComparator {
    int compare(Order baseOrder, Order otherOrder);
}
