package emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces.repositories;


import emSeco.brokerUnit.core.entities.settlementResult.SettlementResult;

import java.util.List;

public interface ISettlementResultRepository {
    void add(List<SettlementResult> settlementResults);
    List<SettlementResult> get();
}
