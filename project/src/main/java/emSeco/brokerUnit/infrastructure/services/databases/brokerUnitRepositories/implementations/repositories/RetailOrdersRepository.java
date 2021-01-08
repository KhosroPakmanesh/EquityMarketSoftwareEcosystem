package emSeco.brokerUnit.infrastructure.services.databases.brokerUnitRepositories.implementations.repositories;

import emSeco.brokerUnit.core.entities.order.RetailOrder;
import emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces.repositories.IRetailOrdersRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RetailOrdersRepository implements IRetailOrdersRepository {
    private final List<RetailOrder> retailOrders;

    public RetailOrdersRepository() {
        this.retailOrders = new ArrayList<>();
    }

    @Override
    public void add(RetailOrder retailOrder) {
        retailOrders.add(retailOrder);
    }

    @Override
    public RetailOrder get(UUID orderId) {
        return retailOrders.stream().
                filter(institutionalOrder -> institutionalOrder.getOrderId() == orderId)
                .findAny().orElse(null);
    }
}
