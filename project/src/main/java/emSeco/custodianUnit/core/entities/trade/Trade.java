package emSeco.custodianUnit.core.entities.trade;

import emSeco.custodianUnit.core.entities.shared.InitiatorType;
import emSeco.custodianUnit.core.entities.shared.SideName;

import java.util.Date;
import java.util.UUID;

public class Trade {
    private UUID exchangeId;
    private final UUID tradeId;
    private final Date tradeTimestamp;
    private final TradeSide buySide;
    private final TradeSide sellSide;
    private SideName tradeOwnerSideName;

    public Trade(UUID exchangeId, UUID tradeId, Date tradeTimestamp, TradeSide buySide, TradeSide sellSide) {
        this.exchangeId = exchangeId;
        this.tradeId = tradeId;
        this.tradeTimestamp = tradeTimestamp;
        this.buySide = buySide;
        this.sellSide = sellSide;
    }

    public Trade(UUID exchangeId, UUID tradeId, Date tradeTimestamp,
                 TradeSide buySide, TradeSide sellSide, SideName tradeOwnerSideName) {
        this.exchangeId = exchangeId;
        this.tradeId = tradeId;
        this.tradeTimestamp = tradeTimestamp;
        this.buySide = buySide;
        this.sellSide = sellSide;
        this.tradeOwnerSideName = tradeOwnerSideName;
    }

    public UUID getTradeId() {
        return tradeId;
    }

    public void setExchangeId(UUID exchangeId) {
        this.exchangeId = exchangeId;
    }

    public Date getTradeTimestamp() {
        return tradeTimestamp;
    }

    public UUID getExchangeId() {
        return exchangeId;
    }

    public TradeSide getBuySide() {
        return buySide;
    }

    public TradeSide getSellSide() {
        return sellSide;
    }

    public InitiatorType getInitiatorType() {
        if (buySide.getTradingInformation().getInitiatorType() == InitiatorType.institutional ||
                sellSide.getTradingInformation().getInitiatorType() == InitiatorType.institutional) {
            return InitiatorType.institutional;
        }

        return InitiatorType.retail;
    }

    public SideName getTradeOwnerSideName() {
        return tradeOwnerSideName;
    }
}
