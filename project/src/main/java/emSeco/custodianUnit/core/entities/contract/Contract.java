package emSeco.custodianUnit.core.entities.contract;


import emSeco.custodianUnit.core.entities.shared.Side;

import java.util.Date;
import java.util.UUID;

public class Contract {
    private final UUID initialTradeId;
    private final UUID contractId;
    private final Date tradeTimestamp;
    private final Side buySide;
    private final Side sellSide;

    public Contract(UUID exchangeId, UUID InitialTradeId, UUID contractId, Date tradeTimestamp,
                    Side buySide, Side sellSide) {
        this.exchangeId = exchangeId;
        this.initialTradeId = InitialTradeId;
        this.contractId = contractId;
        this.tradeTimestamp = tradeTimestamp;
        this.buySide = buySide;
        this.sellSide = sellSide;
    }

    public UUID getContractId() {
        return contractId;
    }

    public Date getTradeTimestamp() {
        return tradeTimestamp;
    }

    public Side getBuySide() {
        return buySide;
    }

    public Side getSellSide() {
        return sellSide;
    }

    public UUID getExchangeId() {
        return exchangeId;
    }

    private final UUID exchangeId;

    public UUID getInitialTradeId() {
        return initialTradeId;
    }
}
