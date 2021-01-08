package emSeco.brokerUnit.core.entities.order;


import emSeco.brokerUnit.core.entities.shared.SideName;

import java.util.Date;

public class OrderTradingInformation {
    private final SideName sideName;
    private final OrderType orderType;
    private final Date orderInitiationDate;
    public OrderTradingInformation(SideName sideName, OrderType orderType, Date orderInitiationDate) {
        this.sideName = sideName;
        this.orderType = orderType;
        this.orderInitiationDate = orderInitiationDate;
    }

    public SideName getSideName() {
        return sideName;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public Date getOrderInitiationDate() {
        return orderInitiationDate;
    }
}
