package emSeco.exchangeUnit.core.entities.order;

import emSeco.exchangeUnit.core.entities.shared.*;

import java.util.Date;

public class Order {
    private final RoutingInformation routingInformation;
    private final AccountsInformation accountsInformation;
    private final OrderTradingInformation tradingInformation;
    private final Term term;
    private final Date orderTimeStamp;

    public Order(RoutingInformation routingInformation, AccountsInformation accountsInformation,
                 OrderTradingInformation tradingInformation, Term term, Date orderTimeStamp) {
        this.routingInformation = routingInformation;
        this.accountsInformation = accountsInformation;
        this.tradingInformation = tradingInformation;
        this.term=term;
        this.orderTimeStamp = orderTimeStamp;
    }
    public boolean hasLeftOver() {
        return this.term.getQuantity() > 0;
    }

    public InitiatorType getInitiatorType() {
        if (tradingInformation.getRegisteredCode() != null &&
                tradingInformation.getClientTradingCode() == null) {
            return InitiatorType.institutional;
        } else if (tradingInformation.getClientTradingCode() != null &&
                tradingInformation.getRegisteredCode() == null) {
            return InitiatorType.retail;
        } else {
            return null;
        }
    }

    public RoutingInformation getRoutingInformation() {
        return routingInformation;
    }

    public AccountsInformation getAccountsInformation() {
        return accountsInformation;
    }

    public OrderTradingInformation getTradingInformation() {
        return tradingInformation;
    }

    public Term getTerm() {
        return term;
    }

    public Date getOrderTimeStamp() {
        return orderTimeStamp;
    }
}
