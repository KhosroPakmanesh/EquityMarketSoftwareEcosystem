package emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces.repositories;

import emSeco.brokerUnit.core.entities.contract.Contract;

import java.util.List;
import java.util.UUID;

public interface IContractRepository {
    void add(List<Contract> contracts);
    List<Contract> get(List<UUID> affirmedContractIds);
    List<Contract> getAffirmedContracts();
}
