package emSeco.exchangeUnit.core.entities.shared;

import java.util.UUID;

public class AllocationDetailInformation {
    private final UUID allocationDetailBlockId;
    private final UUID allocationDetailId;

    public AllocationDetailInformation(UUID allocationDetailBlockId, UUID allocationDetailId) {
        this.allocationDetailBlockId = allocationDetailBlockId;
        this.allocationDetailId = allocationDetailId;
    }

    public UUID getAllocationDetailBlockId() {
        return allocationDetailBlockId;
    }

    public UUID getAllocationDetailId() {
        return allocationDetailId;
    }
}
