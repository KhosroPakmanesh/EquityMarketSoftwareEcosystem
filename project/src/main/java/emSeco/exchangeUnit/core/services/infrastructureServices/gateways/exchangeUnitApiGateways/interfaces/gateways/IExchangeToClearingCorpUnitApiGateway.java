package emSeco.exchangeUnit.core.services.infrastructureServices.gateways.exchangeUnitApiGateways.interfaces.gateways;

import emSeco.exchangeUnit.core.entities.trade.Trade;
import emSeco.shared.architecturalConstructs.BooleanResultMessages;

import java.util.List;

public interface IExchangeToClearingCorpUnitApiGateway {
    List<BooleanResultMessages> submitRetailTrades(List<Trade> trades);
}
