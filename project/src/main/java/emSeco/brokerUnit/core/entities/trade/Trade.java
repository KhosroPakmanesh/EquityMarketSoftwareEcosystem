package emSeco.brokerUnit.core.entities.trade;

import emSeco.brokerUnit.core.entities.shared.InitiatorType;
import emSeco.brokerUnit.core.entities.shared.Side;
import emSeco.brokerUnit.core.entities.shared.SideName;

import java.util.Date;
import java.util.UUID;

public class Trade {
    private final UUID tradeId;
    private UUID exchangeId;
    private final Date tradeTimestamp;
    private final Side buySide;
    private final Side sellSide;
    private SideName tradeOwnerSide;


    public Trade(UUID exchangeId, UUID tradeId, Date tradeTimestamp,
                 Side buySide, Side sellSide) {
        this.exchangeId = exchangeId;
        this.tradeId = tradeId;
        this.tradeTimestamp = tradeTimestamp;
        this.buySide = buySide;
        this.sellSide = sellSide;
    }

    public Trade(UUID exchangeId, UUID tradeId, Date tradeTimestamp,
                 Side buySide, Side sellSide, SideName tradeOwnerSide) {
        this.exchangeId = exchangeId;
        this.tradeId = tradeId;
        this.tradeTimestamp = tradeTimestamp;
        this.buySide = buySide;
        this.sellSide = sellSide;
        this.tradeOwnerSide = tradeOwnerSide;
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

    public Side getBuySide() {
        return buySide;
    }

    public Side getSellSide() {
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
        return tradeOwnerSide;
    }
}
