package emSeco.custodianUnit.core.modules.allocationDetailValidator.interfaces;

import emSeco.custodianUnit.core.entities.allocationDetail.AllocationDetail;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;


import java.util.List;

public interface IAllocationDetailValidator {
    List<BooleanResultMessage> validateAllocationDetails(List<AllocationDetail> allocationDetails);
}
