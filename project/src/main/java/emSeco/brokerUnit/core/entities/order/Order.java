package emSeco.brokerUnit.core.entities.order;

import emSeco.brokerUnit.core.entities.shared.EquityTransferMethod;
import emSeco.brokerUnit.core.entities.shared.MoneyTransferMethod;
import emSeco.brokerUnit.core.entities.shared.Term;

import java.util.UUID;

public class Order {
    protected UUID orderId ;
    protected OrderRoutingInformation orderRoutingInformation;
    protected OrderTradingInformation orderTradingInformation;
    protected MoneyTransferMethod moneyTransferMethod;
    protected EquityTransferMethod equityTransferMethod;
    protected Term term;
    protected boolean quantityDisclosureStatus;

    public UUID getOrderId() {
        return orderId;
    }
    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }
    public OrderRoutingInformation getRoutingInformation() {
        return orderRoutingInformation;
    }
    public OrderTradingInformation getTradingInformation() {
        return orderTradingInformation;
    }
    public void setRoutingInformation(OrderRoutingInformation orderRoutingInformation) {
        this.orderRoutingInformation = orderRoutingInformation;
    }
    public void setTradingInformation(OrderTradingInformation orderTradingInformation) {
        this.orderTradingInformation = orderTradingInformation;
    }
    public void setMoneyTransferMethod(MoneyTransferMethod moneyTransferMethod) {
        this.moneyTransferMethod = moneyTransferMethod;
    }
    public EquityTransferMethod getEquityTransferMethod() {
        return equityTransferMethod;
    }
    public void setEquityTransferMethod(EquityTransferMethod equityTransferMethod) {
        this.equityTransferMethod = equityTransferMethod;
    }
    public MoneyTransferMethod getMoneyTransferMethod() {
        return moneyTransferMethod;
    }
    public Term getTerm() {
        return term;
    }
    public boolean getQuantityDisclosureStatus() {
        return quantityDisclosureStatus;
    }
}
