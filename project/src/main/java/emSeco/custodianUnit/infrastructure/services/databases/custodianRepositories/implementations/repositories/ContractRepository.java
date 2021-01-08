package emSeco.custodianUnit.infrastructure.services.databases.custodianRepositories.implementations.repositories;

import emSeco.custodianUnit.core.entities.contract.Contract;
import emSeco.custodianUnit.core.services.infrastructureServices.databases.custodianUnitRepositories.interfaces.repositories.IContractRepository;

import java.util.ArrayList;
import java.util.List;

public class ContractRepository implements IContractRepository {

    private final List<Contract> contracts;

    public ContractRepository() {
        this.contracts = new ArrayList<>();
    }

    @Override
    public void add(List<Contract> contracts) {
        this.contracts.addAll(contracts);
    }

    @Override
    public List<Contract> get() {
        return contracts;
    }
}
