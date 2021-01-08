package emSeco.exchangeUnit.core.entities.exchangeUnitInfo;

import java.util.UUID;

public class ExchangeUnitInfo {
    private final UUID exchangeId;

    public ExchangeUnitInfo(UUID exchangeId) {
        this.exchangeId = exchangeId;
    }

    public UUID getExchangeId() {
        return exchangeId;
    }
}
