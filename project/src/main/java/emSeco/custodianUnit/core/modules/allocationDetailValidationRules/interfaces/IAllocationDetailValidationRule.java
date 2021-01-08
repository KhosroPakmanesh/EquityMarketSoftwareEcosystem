package emSeco.custodianUnit.core.modules.allocationDetailValidationRules.interfaces;

import emSeco.custodianUnit.core.entities.allocationDetail.AllocationDetail;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;


import java.util.List;

public interface IAllocationDetailValidationRule {
    BooleanResultMessage checkRule(List<AllocationDetail> allocationDetails);
}
