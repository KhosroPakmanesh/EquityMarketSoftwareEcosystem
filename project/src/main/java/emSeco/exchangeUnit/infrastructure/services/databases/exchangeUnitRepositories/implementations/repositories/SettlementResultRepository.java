package emSeco.exchangeUnit.infrastructure.services.databases.exchangeUnitRepositories.implementations.repositories;

import emSeco.exchangeUnit.core.entities.settlementResult.SettlementResult;
import emSeco.exchangeUnit.core.services.infrastructureServices.databases.exchangeUnitRepositories.interfaces.repositories.ISettlementResultRepository;

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
