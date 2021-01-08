package emSeco.brokerUnit.core.entities.order;

import emSeco.brokerUnit.core.entities.shared.RoutingInformation;

import java.util.UUID;

public class OrderRoutingInformation extends RoutingInformation {
    private UUID exchangeId;

    public OrderRoutingInformation(UUID brokerId, UUID exchangeId, UUID custodianId) {
        super(brokerId,exchangeId);
        this.brokerId = brokerId;
        this.exchangeId = exchangeId;
        this.custodianId = custodianId;
    }


    public UUID getBrokerId() {
        return brokerId;
    }

    public UUID getExchangeId() {
        return exchangeId;
    }

    public UUID getCustodianId() {
        return custodianId;
    }

    public void setExchangeId(UUID exchangeId) {
        this.exchangeId=exchangeId;
    }
}
