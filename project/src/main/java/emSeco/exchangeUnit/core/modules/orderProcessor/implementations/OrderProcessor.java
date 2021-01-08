package emSeco.exchangeUnit.core.modules.orderProcessor.implementations;

import com.google.inject.Inject;
import emSeco.exchangeUnit.core.entities.order.Order;
import emSeco.exchangeUnit.core.entities.orderBook.OrderBook;
import emSeco.exchangeUnit.core.entities.trade.Trade;
import emSeco.exchangeUnit.core.modules.orderProcessor.interfaces.IOrderProcessor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class OrderProcessor implements IOrderProcessor {
    private final OrderBook orderBook;
    private final Queue<Order> pendingOrderQueue;

    @Inject
    public OrderProcessor(OrderBook orderBook) {
        this.orderBook = orderBook;
        this.pendingOrderQueue = new LinkedList<>();
    }

    public void submitOrder(Order newOrder) {
        pendingOrderQueue.add(newOrder);
    }

    public List<Trade> processOrders() {
        List<Trade> trades = new ArrayList<>();

        while (!pendingOrderQueue.isEmpty()) {
            Order order = pendingOrderQueue.poll();

            trades.addAll(orderBook.makeTrade(order));
        }

        return trades;
    }
}
