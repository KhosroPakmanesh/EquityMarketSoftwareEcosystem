package emSeco.exchangeUnit.core.modules.orderFactory.interfaces;

import emSeco.exchangeUnit.core.entities.order.Order;
import emSeco.exchangeUnit.core.modules.orderFactory.models.ConstructOrderOutputClass;

public interface IOrderFactory {
    ConstructOrderOutputClass constructOrder(Order order);
}
