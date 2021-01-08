package emSeco.exchangeUnit.infrastructure.services.databases.exchangeUnitRepositories.implementations;

import com.google.inject.Inject;
import emSeco.exchangeUnit.core.services.infrastructureServices.databases.exchangeUnitRepositories.interfaces.repositories.IExchangeUnitInfoRepository;
import emSeco.exchangeUnit.core.services.infrastructureServices.databases.exchangeUnitRepositories.interfaces.IExchangeUnitRepositories;
import emSeco.exchangeUnit.core.services.infrastructureServices.databases.exchangeUnitRepositories.interfaces.repositories.ITradeRepository;
import emSeco.exchangeUnit.core.services.infrastructureServices.databases.exchangeUnitRepositories.interfaces.repositories.ISettlementResultRepository;

public class ExchangeUnitRepositories implements IExchangeUnitRepositories {

    private final ITradeRepository tradeRepository;
    private final ISettlementResultRepository settlementResultRepository;
    private final IExchangeUnitInfoRepository exchangeUnitInfoRepository;

    @Inject
    public ExchangeUnitRepositories(ITradeRepository tradeRepository,
                                    ISettlementResultRepository settlementResultRepository,
                                    IExchangeUnitInfoRepository exchangeUnitInfoRepository) {
        this.tradeRepository = tradeRepository;
        this.settlementResultRepository = settlementResultRepository;
        this.exchangeUnitInfoRepository = exchangeUnitInfoRepository;
    }

    @Override
    public ISettlementResultRepository getSettlementResultRepository() {
        return settlementResultRepository;
    }

    @Override
    public ITradeRepository getTradeRepository() {
        return tradeRepository;
    }

    @Override
    public IExchangeUnitInfoRepository getExchangeUnitInfoRepository() {
        return exchangeUnitInfoRepository;
    }
}
