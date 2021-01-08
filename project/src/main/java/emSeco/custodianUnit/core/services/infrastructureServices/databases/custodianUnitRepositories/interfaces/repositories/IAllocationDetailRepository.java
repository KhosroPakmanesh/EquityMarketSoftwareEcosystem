package emSeco.custodianUnit.core.services.infrastructureServices.databases.custodianUnitRepositories.interfaces.repositories;

import emSeco.custodianUnit.core.entities.allocationDetail.AllocationDetail;

import java.util.List;
import java.util.UUID;

public interface IAllocationDetailRepository {
    void add(List<AllocationDetail> allocationDetail);
    AllocationDetail get(UUID allocationDetailId);
    List<AllocationDetail> get();
}
