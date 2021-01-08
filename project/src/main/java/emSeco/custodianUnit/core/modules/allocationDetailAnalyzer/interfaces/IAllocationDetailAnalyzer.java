package emSeco.custodianUnit.core.modules.allocationDetailAnalyzer.interfaces;

import emSeco.custodianUnit.core.entities.allocationDetail.AllocationDetail;
import emSeco.custodianUnit.core.entities.contract.Contract;
import emSeco.custodianUnit.core.entities.shared.SideName;
import emSeco.custodianUnit.core.modules.allocationDetailAnalyzer.models.AnalyzeAllocationDetailOutputClass;

import java.util.List;

public interface IAllocationDetailAnalyzer {
    AnalyzeAllocationDetailOutputClass analyzeAllocationDetail
            (List<Contract> contracts, List<AllocationDetail> allocationDetails, SideName sideName);
}
