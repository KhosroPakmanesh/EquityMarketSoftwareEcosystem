package emSeco.exchangeUnit.core.entities.orderBook;

import emSeco.exchangeUnit.core.entities.order.Order;
import emSeco.exchangeUnit.core.entities.shared.SideName;

public class OrderTypeContainer {
    private String orderType;
    private final OrderArrayList buyOrders;
    private final OrderArrayList sellOrders;

    public OrderTypeContainer(String orderType) {
        this.buyOrders = new OrderArrayList();
        this.sellOrders = new OrderArrayList();
        this.orderType = orderType;
    }

    public OrderTypeContainer(Order order) {
        this.buyOrders = new OrderArrayList();
        this.sellOrders = new OrderArrayList();

        this.orderType = order.getTradingInformation().getOrderType();
        if (order.getTradingInformation().getSideName() == SideName.buy){
            buyOrders.add(order);
        }
        else {
            sellOrders.add(order);
        }
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderType() {
        return orderType;
    }

    public OrderArrayList getBuyOrders() {
        return buyOrders;
    }

    public OrderArrayList getSellOrders() {
        return sellOrders;
    }
}


