package emSeco.exchangeUnit.core.modules.orderProcessor.interfaces;

import emSeco.exchangeUnit.core.entities.order.Order;
import emSeco.exchangeUnit.core.entities.trade.Trade;

import java.util.List;

public interface IOrderProcessor {
    void submitOrder(Order newOrder);
    List<Trade> processOrders();
}
