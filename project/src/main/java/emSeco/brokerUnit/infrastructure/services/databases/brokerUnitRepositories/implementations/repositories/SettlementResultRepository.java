package emSeco.brokerUnit.infrastructure.services.databases.brokerUnitRepositories.implementations.repositories;

import emSeco.brokerUnit.core.entities.settlementResult.SettlementResult;
import emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces.repositories.ISettlementResultRepository;

import java.util.ArrayList;
import java.util.List;

public class SettlementResultRepository implements ISettlementResultRepository {

    private final List<SettlementResult> settlementResults;

    public SettlementResultRepository() {
        this.settlementResults = new ArrayList<>();
    }

    @Override
    public void add(List<SettlementResult> settlementResults) {
        this.settlementResults.addAll(settlementResults);
    }

    @Override
    public List<SettlementResult> get() {
        return this.settlementResults;
    }
}
