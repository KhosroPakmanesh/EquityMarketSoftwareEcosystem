package emSeco.clearingCropUnit.core.services.infrastructureServices.databases.clearingCorpUnitRepositories.interfaces.repositories;

import emSeco.clearingCropUnit.core.entities.trade.Trade;

import java.util.List;

public interface ITradeRepository {
    void add(List<Trade> trades);
    List<Trade> get();
}
