package emSeco.brokerUnit.core.modules.broker.models;

import emSeco.brokerUnit.core.entities.shared.InitiatorType;

import java.util.UUID;

public class SubmitAllocationDetailsInputClass {
    public UUID getAllocationDetailBlockId() {
        return allocationDetailBlockId;
    }

    public UUID getClientTradingCode() {
        return clientTradingCode;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public UUID getInitialOrderId() {
        return orderId;
    }

    private final UUID custodianId;
    private final UUID allocationDetailId;
    private final UUID allocationDetailBlockId;
    private final UUID clientTradingCode;
    private final UUID registeredCode;
    private final UUID orderId;
    private final InitiatorType orderInitiatorType;
    private final String instrumentName;
    private final double price;
    private final int quantity;

    public SubmitAllocationDetailsInputClass(UUID custodianId, UUID allocationDetailId, UUID allocationDetailBlockId, UUID clientTradingCode,
                                             UUID registeredCode, UUID orderId,
                                             InitiatorType orderInitiatorType, String instrumentName, double price, int quantity) {
        this.custodianId = custodianId;
        this.allocationDetailId = allocationDetailId;
        this.allocationDetailBlockId = allocationDetailBlockId;
        this.clientTradingCode = clientTradingCode;
        this.registeredCode = registeredCode;
        this.orderId = orderId;
        this.orderInitiatorType = orderInitiatorType;
        this.instrumentName = instrumentName;
        this.price = price;
        this.quantity = quantity;
    }

    public InitiatorType getOrderInitiatorType() {
        return orderInitiatorType;
    }

    public UUID getCustodianId() {
        return custodianId;
    }

    public UUID getRegisteredCode() {
        return registeredCode;
    }

    public UUID getAllocationDetailId() {
        return allocationDetailId;
    }
}
