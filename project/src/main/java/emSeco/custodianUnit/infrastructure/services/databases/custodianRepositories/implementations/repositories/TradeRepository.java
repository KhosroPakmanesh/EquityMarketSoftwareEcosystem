package emSeco.custodianUnit.infrastructure.services.databases.custodianRepositories.implementations.repositories;

import emSeco.custodianUnit.core.entities.trade.Trade;
import emSeco.custodianUnit.core.services.infrastructureServices.databases.custodianUnitRepositories.interfaces.repositories.ITradeRepository;

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
