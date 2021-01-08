package emSeco.exchangeUnit.infrastructure.services.gateways.exchangeUnitApiGateways.implementations;

import com.google.inject.Inject;
import emSeco.exchangeUnit.core.services.infrastructureServices.gateways.exchangeUnitApiGateways.interfaces.gateways.IExchangeToBrokerUnitApiGateway;
import emSeco.exchangeUnit.core.services.infrastructureServices.gateways.exchangeUnitApiGateways.interfaces.gateways.IExchangeToClearingCorpUnitApiGateway;
import emSeco.exchangeUnit.core.services.infrastructureServices.gateways.exchangeUnitApiGateways.interfaces.IExchangeUnitApiGateways;

public class ExchangeUnitApiGateways implements IExchangeUnitApiGateways {
    private final IExchangeToClearingCorpUnitApiGateway exchangeToClearingCorpUnitApiGateway;
    private final IExchangeToBrokerUnitApiGateway exchangeToBrokerUnitApiGateway;

    @Inject
    public ExchangeUnitApiGateways(IExchangeToClearingCorpUnitApiGateway exchangeToClearingCorpUnitApiGateway,
                                   IExchangeToBrokerUnitApiGateway exchangeToBrokerUnitApiGateway) {
        this.exchangeToClearingCorpUnitApiGateway = exchangeToClearingCorpUnitApiGateway;
        this.exchangeToBrokerUnitApiGateway = exchangeToBrokerUnitApiGateway;
    }

    @Override
    public IExchangeToClearingCorpUnitApiGateway getExchangeToClearingCorpUnitApiGateway() {
        return exchangeToClearingCorpUnitApiGateway;
    }

    @Override
    public IExchangeToBrokerUnitApiGateway getExchangeToBrokerUnitApiGateway() {
        return exchangeToBrokerUnitApiGateway;
    }
}
