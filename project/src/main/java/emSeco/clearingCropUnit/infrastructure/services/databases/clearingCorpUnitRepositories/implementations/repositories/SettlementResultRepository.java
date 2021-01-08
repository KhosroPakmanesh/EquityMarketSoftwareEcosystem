package emSeco.clearingCropUnit.infrastructure.services.databases.clearingCorpUnitRepositories.implementations.repositories;

import emSeco.clearingCropUnit.core.entities.settlementResult.SettlementResult;
import emSeco.clearingCropUnit.core.services.infrastructureServices.databases.clearingCorpUnitRepositories.interfaces.repositories.ISettlementResultRepository;

import java.util.ArrayList;
import java.util.List;

public class SettlementResultRepository implements ISettlementResultRepository {

    private final List<SettlementResult> settlementResults;

    public List<SettlementResult> getSettlementResults() {
        return settlementResults;
    }

    public SettlementResultRepository() {
        this.settlementResults = new ArrayList<>();
    }

    @Override
    public void add(List<SettlementResult> settlementResults) {
        this.settlementResults.addAll(settlementResults);
    }
}
