package emSeco.clearingCropUnit.core.entities.trade;

import emSeco.clearingCropUnit.core.entities.shared.Side;
import emSeco.clearingCropUnit.core.entities.shared.SideName;


import java.util.Date;
import java.util.UUID;

public class Trade {
    private final UUID exchangeId;
    private final UUID tradeId;
    private final Date tradeTimestamp;
    private final Side buySide;
    private final Side sellSide;
    private SideName tradeOwnerSideName;

    public Trade(UUID exchangeId, UUID tradeId, Date tradeTimestamp,
                 Side buySide, Side sellSide) {
        this.tradeId = tradeId;
        this.exchangeId = exchangeId;
        this.tradeTimestamp = tradeTimestamp;
        this.buySide = buySide;
        this.sellSide = sellSide;
    }

    public Trade(UUID exchangeId, UUID tradeId, Date tradeTimestamp,
                 Side buySide, Side sellSide, SideName tradeOwnerSideName) {
        this.tradeId = tradeId;
        this.exchangeId = exchangeId;
        this.tradeTimestamp = tradeTimestamp;
        this.buySide = buySide;
        this.sellSide = sellSide;
        this.tradeOwnerSideName = tradeOwnerSideName;
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

    public UUID getTradeId() {
        return tradeId;
    }

    public SideName getTradeOwnerSideName() {
        return tradeOwnerSideName;
    }

    public InstitutionalTradeType getInstitutionalTradeType() {
        if ((buySide.getRoutingInformation().getCustodianId() != null &&
                sellSide.getRoutingInformation().getCustodianId() == null) ||
                (buySide.getRoutingInformation().getCustodianId() == null &&
                        sellSide.getRoutingInformation().getCustodianId() != null)) {
            return InstitutionalTradeType.oneSideInstitutional;
        } else if ((buySide.getRoutingInformation().getCustodianId() != null &&
                sellSide.getRoutingInformation().getCustodianId() != null)) {
            return InstitutionalTradeType.twoSideInstitutional;
        } else {
            return InstitutionalTradeType.TwoSideRetail;
        }
    }

    public Side getTradeOwnerSide() {
        if (tradeOwnerSideName == SideName.buy) {
            return buySide;
        } else {
            return sellSide;
        }
    }
}
