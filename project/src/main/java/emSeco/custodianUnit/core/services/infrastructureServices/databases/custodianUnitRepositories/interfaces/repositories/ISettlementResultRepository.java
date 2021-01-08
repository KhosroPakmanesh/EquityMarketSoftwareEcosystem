package emSeco.custodianUnit.core.services.infrastructureServices.databases.custodianUnitRepositories.interfaces.repositories;

import emSeco.custodianUnit.core.entities.settlementResult.SettlementResult;

import java.util.List;

public interface ISettlementResultRepository {
    void add(List<SettlementResult> settlementResults);
    List<SettlementResult> get();
}
