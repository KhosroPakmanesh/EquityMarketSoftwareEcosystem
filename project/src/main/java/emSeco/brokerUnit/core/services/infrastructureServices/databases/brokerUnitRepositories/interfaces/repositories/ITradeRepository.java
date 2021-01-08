package emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces.repositories;

import emSeco.brokerUnit.core.entities.trade.Trade;

import java.util.List;
import java.util.UUID;

public interface ITradeRepository  {
    void add(List<Trade> trades);
    List<Trade> get(UUID orderId);
    List<Trade> getInstitutionalTrades();
}
