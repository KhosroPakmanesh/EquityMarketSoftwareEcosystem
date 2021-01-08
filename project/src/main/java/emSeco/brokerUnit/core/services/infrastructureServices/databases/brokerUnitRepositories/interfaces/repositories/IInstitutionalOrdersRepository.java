package emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces.repositories;

import emSeco.brokerUnit.core.entities.order.InstitutionalOrder;

import java.util.UUID;

public interface IInstitutionalOrdersRepository {
    void add(InstitutionalOrder institutionalOrder) ;
    InstitutionalOrder get(UUID orderId);
}
