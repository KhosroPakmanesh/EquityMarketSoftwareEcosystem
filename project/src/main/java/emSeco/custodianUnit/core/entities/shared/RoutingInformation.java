package emSeco.custodianUnit.core.entities.shared;

import java.util.UUID;

public class RoutingInformation {

    private final UUID brokerId;
    private final UUID custodianId;

    public RoutingInformation(UUID brokerId, UUID custodianId) {
        this.brokerId = brokerId;

        this.custodianId = custodianId;
    }

    public UUID getBrokerId() {
        return brokerId;
    }
    public UUID getCustodianId() {
        return custodianId;
    }
}
