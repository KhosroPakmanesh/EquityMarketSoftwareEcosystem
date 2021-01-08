package emSeco.custodianUnit.core.services.infrastructureServices.databases.custodianUnitRepositories.interfaces.repositories;

import emSeco.custodianUnit.core.entities.trade.Trade;

import java.util.List;

public interface ITradeRepository {
    void add(List<Trade> trades);
    List<Trade> get();
}
