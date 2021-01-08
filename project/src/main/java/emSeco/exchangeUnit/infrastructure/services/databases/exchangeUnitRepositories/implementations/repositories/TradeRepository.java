package emSeco.exchangeUnit.infrastructure.services.databases.exchangeUnitRepositories.implementations.repositories;

import emSeco.exchangeUnit.core.entities.trade.Trade;
import emSeco.exchangeUnit.core.services.infrastructureServices.databases.exchangeUnitRepositories.interfaces.repositories.ITradeRepository;

import java.util.ArrayList;
import java.util.List;

public class TradeRepository implements ITradeRepository {
    private final List<Trade> trades;

    public TradeRepository() {
        this.trades = new ArrayList<>();
    }

    @Override
    public void add(List<Trade> trades) {
        this.trades.addAll(trades);
    }

    @Override
    public List<Trade> get() {
        return trades;
    }
}
