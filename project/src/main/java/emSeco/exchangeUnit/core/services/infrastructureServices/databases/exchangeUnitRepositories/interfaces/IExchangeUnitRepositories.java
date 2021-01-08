package emSeco.exchangeUnit.core.services.infrastructureServices.databases.exchangeUnitRepositories.interfaces;

import emSeco.exchangeUnit.core.services.infrastructureServices.databases.exchangeUnitRepositories.interfaces.repositories.IExchangeUnitInfoRepository;
import emSeco.exchangeUnit.core.services.infrastructureServices.databases.exchangeUnitRepositories.interfaces.repositories.ITradeRepository;
import emSeco.exchangeUnit.core.services.infrastructureServices.databases.exchangeUnitRepositories.interfaces.repositories.ISettlementResultRepository;

public interface IExchangeUnitRepositories {
    ISettlementResultRepository getSettlementResultRepository();
    ITradeRepository getTradeRepository();
    IExchangeUnitInfoRepository getExchangeUnitInfoRepository();
}
