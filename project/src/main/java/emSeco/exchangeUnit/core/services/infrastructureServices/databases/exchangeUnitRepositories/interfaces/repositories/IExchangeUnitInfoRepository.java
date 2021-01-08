package emSeco.exchangeUnit.core.services.infrastructureServices.databases.exchangeUnitRepositories.interfaces.repositories;

import emSeco.exchangeUnit.core.entities.exchangeUnitInfo.ExchangeUnitInfo;

public interface IExchangeUnitInfoRepository {
    ExchangeUnitInfo get();
    void add(ExchangeUnitInfo exchangeUnitInfo);
}
