package emSeco.custodianUnit.core.entities.settlementResult;

import emSeco.custodianUnit.core.entities.trade.Trade;

import java.util.Date;

public class SettlementResult {
    public Trade getTrade() {
        return trade;
    }

    public Date getTradeSettlementTimestamp() {
        return tradeSettlementTimestamp;
    }

    private final Trade trade;
    private final Date tradeSettlementTimestamp;

    public SettlementResult(Trade trade, Date tradeSettlementTimestamp) {
        this.trade = trade;
        this.tradeSettlementTimestamp = tradeSettlementTimestamp;
    }
}
