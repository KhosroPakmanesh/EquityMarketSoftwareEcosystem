package emSeco.exchangeUnit.core.services.infrastructureServices.gateways.exchangeUnitApiGateways.interfaces.gateways;

import emSeco.exchangeUnit.core.entities.trade.Trade;
import emSeco.exchangeUnit.core.entities.settlementResult.SettlementResult;
import emSeco.shared.architecturalConstructs.BooleanResultMessages;

import java.util.List;

public interface IExchangeToBrokerUnitApiGateway {
    BooleanResultMessages submitSettlementResults(List<SettlementResult> settlementResults);
    BooleanResultMessages submitTrades(List<Trade> trades);
}
