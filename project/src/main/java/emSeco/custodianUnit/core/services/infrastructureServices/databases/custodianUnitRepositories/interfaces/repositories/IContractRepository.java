package emSeco.custodianUnit.core.services.infrastructureServices.databases.custodianUnitRepositories.interfaces.repositories;

import emSeco.custodianUnit.core.entities.contract.Contract;

import java.util.List;

public interface IContractRepository {
    void add(List<Contract> contracts);
    List<Contract> get();
}
