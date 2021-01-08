package emSeco.brokerUnit.core.entities.contract;

import emSeco.brokerUnit.core.entities.shared.Side;
import emSeco.brokerUnit.core.entities.shared.SideName;

import java.util.Date;
import java.util.UUID;

public class Contract {
    private final UUID exchangeId;
    private final UUID initialTradeId;
    private final UUID contractId;
    private final Date tradeTimestamp;
    private final Side buySide;
    private final Side sellSide;
    private boolean isAffirmed;
    private SideName contractOwnerSide;

    public Contract(UUID exchangeId,UUID InitialTradeId, UUID contractId, Date tradeTimestamp,
                    Side buySide, Side sellSide) {
        this.exchangeId = exchangeId;
        this.initialTradeId = InitialTradeId;
        this.contractId = contractId;
        this.tradeTimestamp = tradeTimestamp;
        this.buySide = buySide;
        this.sellSide = sellSide;
    }

    public Contract(UUID exchangeId, UUID InitialTradeId, UUID contractId, Date tradeTimestamp,
                    Side buySide, Side sellSide, SideName contractOwnerSide) {
        this.exchangeId = exchangeId;
        this.initialTradeId = InitialTradeId;
        this.contractId = contractId;
        this.tradeTimestamp = tradeTimestamp;
        this.buySide = buySide;
        this.sellSide = sellSide;
        this.contractOwnerSide = contractOwnerSide;
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
    public UUID getInitialTradeId() {
        return initialTradeId;
    }
    public boolean getIsAffirmed() {
        return isAffirmed;
    }
    public void setIsAffirmed(boolean accepted) {
        isAffirmed = accepted;
    }
    public SideName getContractOwnerSideName() {
        return contractOwnerSide;
    }
}
