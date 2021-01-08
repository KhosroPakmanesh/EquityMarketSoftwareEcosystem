package emSeco.clearingCropUnit.infrastructure.services.databases.clearingCorpUnitRepositories.implementations.repositories;

import emSeco.clearingCropUnit.core.entities.trade.Trade;
import emSeco.clearingCropUnit.core.services.infrastructureServices.databases.clearingCorpUnitRepositories.interfaces.repositories.ITradeRepository;

import java.util.ArrayList;
import java.util.List;

public class TradeRepository implements ITradeRepository {
    private final List<Trade> trades;

    public TradeRepository() {
        this.trades= new ArrayList<>();
    }

    @Override
    public void add(List<Trade> trades) {
        this.trades.addAll(trades);
    }

    @Override
    public List<Trade>  get() {
        return this.trades;
    }
}
