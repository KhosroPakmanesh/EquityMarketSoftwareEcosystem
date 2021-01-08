package emSeco.brokerUnit.infrastructure.services.databases.brokerUnitRepositories.implementations.repositories;

import emSeco.brokerUnit.core.entities.order.InstitutionalOrder;
import emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces.repositories.IInstitutionalOrdersRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InstitutionalOrdersRepository implements IInstitutionalOrdersRepository {
    private final List<InstitutionalOrder> institutionalOrders;

    public InstitutionalOrdersRepository() {
        this.institutionalOrders = new ArrayList<>();
    }

    @Override
    public void add(InstitutionalOrder institutionalOrder) {
        this.institutionalOrders.add(institutionalOrder);
    }

    @Override
    public InstitutionalOrder get(UUID orderId) {
        return institutionalOrders.stream().
                filter(institutionalOrder -> institutionalOrder.getOrderId() == orderId)
                .findAny().orElse(null);
    }
}
