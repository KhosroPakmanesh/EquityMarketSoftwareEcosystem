package emSeco.exchangeUnit.core.entities.order;

import emSeco.exchangeUnit.core.entities.shared.SideName;

import java.util.UUID;

public class OrderTradingInformation {
    private final UUID initialOrderId;
    private final SideName sideName;
    private final String orderType;
    private final UUID registeredCode;
    private final UUID clientTradingCode;
    private final boolean quantityDisclosureStatus;

    public OrderTradingInformation(UUID initialOrderId, SideName sideName, String orderType,
                                   UUID registeredCode, UUID clientTradingCode,
                                   boolean quantityDisclosureStatus) {
        this.initialOrderId = initialOrderId;
        this.sideName = sideName;
        this.orderType = orderType;
        this.registeredCode = registeredCode;
        this.clientTradingCode = clientTradingCode;
        this.quantityDisclosureStatus = quantityDisclosureStatus;
    }

    public UUID getInitialOrderId() {
        return initialOrderId;
    }

    public SideName getSideName() {
        return sideName;
    }

    public String getOrderType() {
        return orderType;
    }

    public UUID getRegisteredCode() {
        return registeredCode;
    }

    public UUID getClientTradingCode() {
        return clientTradingCode;
    }

    public boolean isQuantityDisclosureStatus() {
        return quantityDisclosureStatus;
    }

    public boolean getQuantityDisclosureStatus() {
        return quantityDisclosureStatus;
    }
}