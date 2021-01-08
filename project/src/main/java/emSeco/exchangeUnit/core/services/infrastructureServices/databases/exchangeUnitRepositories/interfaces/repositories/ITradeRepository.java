package emSeco.exchangeUnit.core.services.infrastructureServices.databases.exchangeUnitRepositories.interfaces.repositories;

import emSeco.exchangeUnit.core.entities.trade.Trade;

import java.util.List;

public interface ITradeRepository {
    void add(List<Trade> trades);
    List<Trade> get();
}
