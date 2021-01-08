package emSeco.exchangeUnit.infrastructure.services.gateways.exchangeUnitApiGateways.implementations.gateways;

import com.google.inject.Inject;
import emSeco.exchangeUnit.core.entities.trade.Trade;
import emSeco.exchangeUnit.core.services.domainServices.exchangeServiceRegistry.interfaces.IExchangeServiceRegistry;
import emSeco.exchangeUnit.core.services.infrastructureServices.gateways.exchangeUnitApiGateways.interfaces.gateways.IExchangeToClearingCorpUnitApiGateway;
import emSeco.shared.architecturalConstructs.BooleanResultMessages;

import java.util.List;

import static emSeco.exchangeUnit.core.helpers.ExchangeToClearingCorpEntitiesMapper.mapFromExchangeTradeToClearingCorpTrade;

public class ExchangeToClearingCorpUnitApiGateway implements IExchangeToClearingCorpUnitApiGateway {
    private final IExchangeServiceRegistry exchangeServiceRegistry;

    @Inject
    public ExchangeToClearingCorpUnitApiGateway(IExchangeServiceRegistry exchangeServiceRegistry) {
        this.exchangeServiceRegistry = exchangeServiceRegistry;
    }

    public List<BooleanResultMessages> submitRetailTrades(List<Trade> trades) {
        return exchangeServiceRegistry.getClearingCorp().submitRetailTrades_API
                (mapFromExchangeTradeToClearingCorpTrade(trades));
    }
}