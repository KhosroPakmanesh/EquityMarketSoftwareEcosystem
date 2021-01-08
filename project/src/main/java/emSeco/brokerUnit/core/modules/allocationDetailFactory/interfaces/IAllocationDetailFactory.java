package emSeco.brokerUnit.core.modules.allocationDetailFactory.interfaces;

import emSeco.brokerUnit.core.modules.broker.models.SubmitAllocationDetailsInputClass;
import emSeco.brokerUnit.core.modules.broker.models.ConstructAllocationDetailOutputClass;

import java.util.List;

public interface IAllocationDetailFactory {
    ConstructAllocationDetailOutputClass constructAllocationDetail
            (List<SubmitAllocationDetailsInputClass> inputClass);
}
