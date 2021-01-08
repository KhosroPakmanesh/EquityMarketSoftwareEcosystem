package emSeco.brokerUnit.infrastructure.services.databases.brokerUnitRepositories.implementations.repositories;

import emSeco.brokerUnit.core.entities.contract.Contract;
import emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces.repositories.IContractRepository;

import java.util.*;
import java.util.stream.Collectors;


public class ContractRepository implements IContractRepository {
    private final List<Contract> contracts;

    public ContractRepository() {
        this.contracts= new ArrayList<>();
    }

    public void add(List<Contract> contracts) {
        this.contracts.addAll(contracts) ;
    }

    @Override
    public List<Contract> get(List<UUID> affirmedContractIds) {
        List<Contract> affirmedContracts=new ArrayList<>();
        for (Contract contract:contracts) {
            for (UUID affirmedContractId:affirmedContractIds) {
                if (contract.getContractId() == affirmedContractId){
                    affirmedContracts.add(contract);
                }
            }
        }

        return affirmedContracts;
    }

    @Override
    public List<Contract> getAffirmedContracts() {
        return this.contracts.stream().filter(Contract::getIsAffirmed)
                .collect(Collectors.toList());
    }
}
