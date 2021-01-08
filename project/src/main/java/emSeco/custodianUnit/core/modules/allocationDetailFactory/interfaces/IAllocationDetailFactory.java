package emSeco.custodianUnit.core.modules.allocationDetailFactory.interfaces;

import emSeco.custodianUnit.core.modules.allocationDetailFactory.models.ConstructAllocationDetailOutputClass;
import emSeco.custodianUnit.core.modules.custodian.models.SubmitAllocationDetailsInputClass;

import java.util.List;

public interface IAllocationDetailFactory {
    ConstructAllocationDetailOutputClass constructAllocationDetail
            (List<SubmitAllocationDetailsInputClass> inputClass) ;
}
