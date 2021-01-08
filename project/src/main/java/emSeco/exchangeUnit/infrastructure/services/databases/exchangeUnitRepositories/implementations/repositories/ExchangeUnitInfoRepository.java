package emSeco.exchangeUnit.infrastructure.services.databases.exchangeUnitRepositories.implementations.repositories;

import emSeco.exchangeUnit.core.entities.exchangeUnitInfo.ExchangeUnitInfo;
import emSeco.exchangeUnit.core.services.infrastructureServices.databases.exchangeUnitRepositories.interfaces.repositories.IExchangeUnitInfoRepository;

public class ExchangeUnitInfoRepository implements IExchangeUnitInfoRepository {

    ExchangeUnitInfo exchangeUnitInfo;

    @Override
    public ExchangeUnitInfo get() {
        return exchangeUnitInfo;
    }

    @Override
    public void add(ExchangeUnitInfo exchangeUnitInfo) {
        this.exchangeUnitInfo=exchangeUnitInfo;
    }
}
