package emSeco.clearingCropUnit.core.entities.settlementResult;

import emSeco.clearingCropUnit.core.entities.trade.Trade;

import java.util.Date;

public class SettlementResult {
    public SettlementResult(Trade trade, Date tradeSettlementTimestamp) {

        this.trade = trade;
        this.tradeSettlementTimestamp = tradeSettlementTimestamp;
    }

    public Trade getTrade() {
        return trade;
    }


    public Date getTradeSettlementTimestamp() {
        return tradeSettlementTimestamp;
    }

    private final Trade trade;
    private final Date tradeSettlementTimestamp;
}
