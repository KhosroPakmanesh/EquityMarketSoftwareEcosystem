package emSeco.custodianUnit.core.modules.custodian.models;


import emSeco.custodianUnit.core.entities.shared.*;

import java.util.UUID;

public class SubmitAllocationDetailsInputClass {
    private final UUID brokerId;
    private final UUID allocationDetailId;
    private final UUID allocationDetailBlockId;
    private final UUID clientClearingBankId;
    private final UUID clientClearingBankAccountNumber;
    private final UUID clientDepositoryId;
    private final UUID clientDematAccountNumber;
    private final UUID clientTradeCode;
    private final UUID registeredCode;
    private final UUID initialOrderId;
    private final String instrumentName;
    private final double price;
    private final int quantity;
    private final MoneyTransferMethod moneyTransferMethod;
    private final EquityTransferMethod equityTransferMethod;

    public SubmitAllocationDetailsInputClass(
            UUID brokerId, UUID allocationDetailId, UUID allocationDetailBlockId,
            UUID clientClearingBankId, UUID clientClearingBankAccountNumber,
            UUID clientDepositoryId, UUID clientDematAccountNumber,
            UUID clientTradeCode, UUID registeredCode, UUID initialOrderId,
            String instrumentName, double price, int quantity,
            MoneyTransferMethod moneyTransferMethod,
            EquityTransferMethod EquityTransferMethod) {
        this.brokerId = brokerId;
        this.allocationDetailId = allocationDetailId;
        this.allocationDetailBlockId = allocationDetailBlockId;
        this.clientClearingBankId = clientClearingBankId;
        this.clientClearingBankAccountNumber = clientClearingBankAccountNumber;
        this.clientDepositoryId = clientDepositoryId;
        this.clientDematAccountNumber = clientDematAccountNumber;
        this.clientTradeCode = clientTradeCode;
        this.registeredCode = registeredCode;
        this.initialOrderId = initialOrderId;
        this.instrumentName = instrumentName;
        this.price = price;
        this.quantity = quantity;
        this.moneyTransferMethod = moneyTransferMethod;
        this.equityTransferMethod = EquityTransferMethod;
    }

    public UUID getBrokerId() {
        return brokerId;
    }

    public UUID getAllocationDetailId() {
        return allocationDetailId;
    }

    public UUID getAllocationDetailBlockId() {
        return allocationDetailBlockId;
    }

    public UUID getClientClearingBankId() {
        return clientClearingBankId;
    }

    public UUID getClientClearingBankAccountNumber() {
        return clientClearingBankAccountNumber;
    }

    public UUID getClientDepositoryId() {
        return clientDepositoryId;
    }

    public UUID getClientDematAccountNumber() {
        return clientDematAccountNumber;
    }

    public UUID getClientTradeCode() {
        return clientTradeCode;
    }

    public UUID getRegisteredCode() {
        return registeredCode;
    }

    public UUID getInitialOrderId() {
        return initialOrderId;
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

    public MoneyTransferMethod getMoneyTransferMethod() {
        return moneyTransferMethod;
    }

    public EquityTransferMethod getEquityTransferMethod() {
        return equityTransferMethod;
    }
}
