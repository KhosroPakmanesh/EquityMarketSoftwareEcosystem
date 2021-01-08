package emSeco.brokerUnit.core.entities.settlementResult;

import emSeco.brokerUnit.core.entities.trade.Trade;

import java.util.Date;

public class SettlementResult {
    private final Trade trade;
    private final Date tradeSettlementTimestamp;

    public SettlementResult(Trade trade, Date tradeSettlementTimestamp) {
        this.trade = trade;
        this.tradeSettlementTimestamp = tradeSettlementTimestamp;
    }

    public Trade getTrade() {
        return trade;
    }
}
