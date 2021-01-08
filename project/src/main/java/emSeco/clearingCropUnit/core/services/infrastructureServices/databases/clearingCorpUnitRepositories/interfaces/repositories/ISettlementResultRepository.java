package emSeco.clearingCropUnit.core.services.infrastructureServices.databases.clearingCorpUnitRepositories.interfaces.repositories;

import emSeco.clearingCropUnit.core.entities.settlementResult.SettlementResult;

import java.util.List;

public interface ISettlementResultRepository {
    void add(List<SettlementResult> settlementResults);
}
