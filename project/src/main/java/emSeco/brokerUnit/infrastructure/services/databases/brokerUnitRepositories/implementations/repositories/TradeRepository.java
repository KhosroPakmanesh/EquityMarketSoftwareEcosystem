package emSeco.brokerUnit.infrastructure.services.databases.brokerUnitRepositories.implementations.repositories;

import emSeco.brokerUnit.core.entities.shared.InitiatorType;
import emSeco.brokerUnit.core.entities.trade.Trade;
import emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces.repositories.ITradeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public List<Trade> get(UUID orderId) {
        return this.trades.stream().
                filter(trade -> trade.getBuySide().getTradingInformation().getInitialOrderId() == orderId ||
                        trade.getSellSide().getTradingInformation().getInitialOrderId() == orderId).
                collect(Collectors.toList());
    }

    @Override
    public List<Trade> getInstitutionalTrades() {
        return this.trades.stream().filter(t -> t.getInitiatorType() == InitiatorType.institutional).
                collect(Collectors.toList());
    }
}
