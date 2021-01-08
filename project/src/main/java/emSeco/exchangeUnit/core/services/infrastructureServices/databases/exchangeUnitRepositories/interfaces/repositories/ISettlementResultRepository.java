package emSeco.exchangeUnit.core.services.infrastructureServices.databases.exchangeUnitRepositories.interfaces.repositories;

import emSeco.exchangeUnit.core.entities.settlementResult.SettlementResult;

import java.util.List;

public interface ISettlementResultRepository {
    void add(List<SettlementResult> settlementResults);
    List<SettlementResult> get();
}
