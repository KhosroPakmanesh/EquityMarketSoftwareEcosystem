package emSeco.brokerUnit.core.modules.broker.models;

import emSeco.brokerUnit.core.entities.shared.EquityTransferMethod;
import emSeco.brokerUnit.core.entities.shared.MoneyTransferMethod;
import emSeco.brokerUnit.core.entities.shared.SideName;
import emSeco.brokerUnit.core.entities.shared.Term;

import java.util.Date;
import java.util.UUID;

public class InitiateRetailOrderInputClass {
    private final UUID exchangeId;
    private final UUID clientTradingCode;
    private final SideName orderSide;
    private final Date orderInitiationDate;
    private final Term orderTerm;
    private final String orderType;
    private final MoneyTransferMethod moneyTransferMethod;
    private final EquityTransferMethod equityTransferMethod;

    public InitiateRetailOrderInputClass(UUID exchangeId, UUID clientTradingCode, SideName orderSide, Date orderInitiationDate,
                                         Term orderTerm, String orderType, MoneyTransferMethod moneyTransferMethod,
                                         EquityTransferMethod equityTransferMethod) {
        this.exchangeId = exchangeId;
        this.clientTradingCode =clientTradingCode;
        this.orderSide= orderSide;
        this.orderInitiationDate=orderInitiationDate;
        this.orderTerm=orderTerm;
        this.orderType = orderType;
        this.moneyTransferMethod = moneyTransferMethod;
        this.equityTransferMethod = equityTransferMethod;
    }

    public UUID getExchangeId() {
        return exchangeId;
    }

    public UUID getClientTradingCode() {
        return clientTradingCode;
    }

    public SideName getOrderSide() {
        return orderSide;
    }

    public Term getOrderTerm() {
        return orderTerm;
    }

    public Date getOrderInitiationDate() {
        return orderInitiationDate;
    }

    public MoneyTransferMethod getMoneyTransferMethod() {
        return moneyTransferMethod;
    }

    public String getOrderType() {
        return orderType;
    }

    public EquityTransferMethod getEquityTransferMethod() {
        return equityTransferMethod;
    }
}
