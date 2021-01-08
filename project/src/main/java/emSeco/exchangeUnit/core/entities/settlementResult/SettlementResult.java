package emSeco.exchangeUnit.core.entities.settlementResult;

import emSeco.exchangeUnit.core.entities.trade.Trade;

import java.util.Date;

public class SettlementResult {
    private Trade trade;
    private Date tradeSettlementTimestamp;

    public SettlementResult() {
    }

    public Trade getTrade() {
        return trade;
    }

    public Date getTradeSettlementTimestamp() {
        return tradeSettlementTimestamp;
    }

    public SettlementResult(Trade trade, Date tradeSettlementTimestamp) {
        this.trade = trade;
        this.tradeSettlementTimestamp = tradeSettlementTimestamp;
    }
}
