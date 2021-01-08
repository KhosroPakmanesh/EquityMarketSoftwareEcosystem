package emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces.repositories;

import emSeco.brokerUnit.core.entities.order.RetailOrder;

import java.util.UUID;

public interface IRetailOrdersRepository {
    void add(RetailOrder retailOrder) ;
    RetailOrder get(UUID orderId);
}
