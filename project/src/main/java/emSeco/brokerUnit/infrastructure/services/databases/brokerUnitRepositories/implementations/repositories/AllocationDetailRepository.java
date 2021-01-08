package emSeco.brokerUnit.infrastructure.services.databases.brokerUnitRepositories.implementations.repositories;

import emSeco.brokerUnit.core.entities.allocationDetail.AllocationDetail;
import emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces.repositories.IAllocationDetailRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AllocationDetailRepository implements IAllocationDetailRepository {
    private final List<AllocationDetail> allocationDetails;

    public AllocationDetailRepository() {
        this.allocationDetails= new ArrayList<>();
    }

    @Override
    public void add(List<AllocationDetail> allocationDetails) {
        this.allocationDetails.addAll(allocationDetails);
    }

    @Override
    public List<AllocationDetail> getAllocationDetails(UUID orderId) {
        return this.allocationDetails.stream().
                filter(ad -> ad.getTradingInformation().getInitialOrderId() == orderId).collect(Collectors.toList());
    }
}
