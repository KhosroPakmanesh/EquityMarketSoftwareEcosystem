package emSeco.custodianUnit.infrastructure.services.databases.custodianRepositories.implementations.repositories;

import emSeco.custodianUnit.core.entities.settlementResult.SettlementResult;
import emSeco.custodianUnit.core.services.infrastructureServices.databases.custodianUnitRepositories.interfaces.repositories.ISettlementResultRepository;

import java.util.ArrayList;
import java.util.List;

public class SettlementResultRepository implements ISettlementResultRepository {
    final List<SettlementResult> settlementResults;
    public SettlementResultRepository() {
        settlementResults = new ArrayList<>();
    }

    @Override
    public void add(List<SettlementResult> settlementResults) {
        this.settlementResults.addAll(settlementResults);
    }

    @Override
    public List<SettlementResult> get() {
        return settlementResults;
    }
}
