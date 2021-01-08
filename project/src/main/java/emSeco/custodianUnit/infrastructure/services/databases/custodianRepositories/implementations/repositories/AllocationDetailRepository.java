package emSeco.custodianUnit.infrastructure.services.databases.custodianRepositories.implementations.repositories;

import emSeco.custodianUnit.core.entities.allocationDetail.AllocationDetail;
import emSeco.custodianUnit.core.services.infrastructureServices.databases.custodianUnitRepositories.interfaces.repositories.IAllocationDetailRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AllocationDetailRepository implements IAllocationDetailRepository {

    private final List<AllocationDetail> allocationDetails;

    public AllocationDetailRepository() {
        allocationDetails = new ArrayList<>();
    }

    @Override
    public void add(List<AllocationDetail> allocationDetail) {
        this.allocationDetails.addAll(allocationDetail);
    }

    @Override
    public AllocationDetail get(UUID allocationDetailId) {
        return allocationDetails.stream().
                filter(allocationDetail -> allocationDetail.getAllocationDetailInformation().
                        getAllocationDetailId() == allocationDetailId).findAny().orElse(null);
    }

    @Override
    public List<AllocationDetail> get() {
        return allocationDetails;
    }
}
