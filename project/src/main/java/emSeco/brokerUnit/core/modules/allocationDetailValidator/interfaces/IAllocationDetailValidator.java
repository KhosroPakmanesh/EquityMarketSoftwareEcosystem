package emSeco.brokerUnit.core.modules.allocationDetailValidator.interfaces;

import emSeco.brokerUnit.core.entities.allocationDetail.AllocationDetail;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;


import java.util.List;

public interface IAllocationDetailValidator {
    List<BooleanResultMessage> validateAllocationDetails(List<AllocationDetail> allocationDetails);
}
