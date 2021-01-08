package emSeco.clearingCropUnit.core.entities.shared;

import java.util.UUID;

public class TradingInformation {
    private final UUID initialOrderId;
    private final InitiatorType initiatorType;
    private final UUID clientTradingCode;
    private final UUID registeredCode;

    public TradingInformation(UUID initialOrderId, InitiatorType initiatorType,
                              UUID clientTradingCode, UUID registeredCode) {
        this.initialOrderId = initialOrderId;
        this.initiatorType = initiatorType;
        this.clientTradingCode = clientTradingCode;
        this.registeredCode = registeredCode;
    }

    public UUID getInitialOrderId() {
        return initialOrderId;
    }

    public InitiatorType getInitiatorType() {
        return initiatorType;
    }

    public UUID getClientTradingCode() {
        return clientTradingCode;
    }

    public UUID getRegisteredCode() {
        return registeredCode;
    }
}
