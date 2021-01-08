package emSeco.exchangeUnit.core.entities.orderBook;

import emSeco.exchangeUnit.core.entities.order.Order;

import java.util.ArrayList;
import java.util.List;

public class InstrumentContainer {
    private final List<OrderTypeContainer> orderTypeContainers;

    public String getInstrumentName() {
        return instrumentName;
    }

    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }

    private String instrumentName;

    public InstrumentContainer(Order order) {
        orderTypeContainers = new ArrayList<>();

        setInstrumentName(order.getTerm().getInstrumentName());
        OrderTypeContainer newOrderTypeContainer = new OrderTypeContainer(order);
        orderTypeContainers.add(newOrderTypeContainer);
    }

    public OrderTypeContainer getOrderTypeContainer(String orderType){
        return  orderTypeContainers.stream().filter(otc ->
                otc.getOrderType().equals(orderType)).findAny().orElse(null);
    }

    public OrderTypeContainer createEmptyOrderContainer(Order order){
        OrderTypeContainer newOrderTypeContainer = new OrderTypeContainer(order.getTradingInformation().getOrderType());
        orderTypeContainers.add(newOrderTypeContainer);
        return newOrderTypeContainer;
    }
}
