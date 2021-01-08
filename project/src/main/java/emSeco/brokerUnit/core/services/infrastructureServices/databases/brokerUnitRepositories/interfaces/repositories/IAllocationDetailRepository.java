package emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces.repositories;

import emSeco.brokerUnit.core.entities.allocationDetail.AllocationDetail;

import java.util.List;
import java.util.UUID;

public interface IAllocationDetailRepository {
    void add(List<AllocationDetail> allocationDetail) ;
    List<AllocationDetail> getAllocationDetails(UUID orderId);
}
