package emSeco.brokerUnit.core.services.infrastructureServices.gateways.brokerUnitApiGateways.interfaces.gateways;

import emSeco.brokerUnit.core.entities.contract.Contract;
import emSeco.brokerUnit.core.entities.trade.Trade;
import emSeco.shared.architecturalConstructs.BooleanResultMessages;

import java.util.List;

public interface IBrokerToCustodianUnitApiGateway {
    BooleanResultMessages submitContracts(List<Contract> contracts);
    BooleanResultMessages submitTrades(List<Trade> trades);
}
