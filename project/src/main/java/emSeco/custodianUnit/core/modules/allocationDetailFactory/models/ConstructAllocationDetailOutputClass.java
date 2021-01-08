package emSeco.custodianUnit.core.modules.allocationDetailFactory.models;

import emSeco.custodianUnit.core.entities.allocationDetail.AllocationDetail;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;


import java.util.List;

public class ConstructAllocationDetailOutputClass {
    private final List<AllocationDetail> allocationDetails;
    private final List<BooleanResultMessage> allocationDetailsValidationResultMessages;

    public ConstructAllocationDetailOutputClass(
            List<BooleanResultMessage> allocationDetailsValidationResultMessages,
            List<AllocationDetail> allocationDetails) {
        this.allocationDetailsValidationResultMessages = allocationDetailsValidationResultMessages;
        this.allocationDetails = allocationDetails;
    }

    public boolean hasErrors() {
        return this.allocationDetailsValidationResultMessages.size()>0;
    }

    public List<BooleanResultMessage> getAllocationDetailsValidationResultMessages() {
        return allocationDetailsValidationResultMessages;
    }

    public List<AllocationDetail> getAllocationDetails() {
        return this.allocationDetails;
    }
}
