package emSeco.exchangeUnit.infrastructure.services.gateways.exchangeUnitApiGateways.implementations.gateways;

import com.google.inject.Inject;
import emSeco.exchangeUnit.core.entities.trade.Trade;
import emSeco.brokerUnit.core.modules.broker.interfaces.IBroker;
import emSeco.exchangeUnit.core.entities.settlementResult.SettlementResult;
import emSeco.exchangeUnit.core.services.domainServices.exchangeServiceRegistry.interfaces.IExchangeServiceRegistry;
import emSeco.exchangeUnit.core.services.infrastructureServices.gateways.exchangeUnitApiGateways.interfaces.gateways.IExchangeToBrokerUnitApiGateway;
import emSeco.shared.architecturalConstructs.BooleanResultMessages;
import emSeco.shared.exceptions.ServiceRegistryMalfunctionException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static emSeco.exchangeUnit.core.helpers.ExchangeToBrokerEntitiesMapper.mapExchangeSettlementResultToBrokerSettlementResult;
import static emSeco.exchangeUnit.core.helpers.ExchangeToBrokerEntitiesMapper.mapExchangeTradeToBrokerTrade;
import static emSeco.shared.architecturalConstructs.BooleanResultMessages.aggregateListOfResultMessages;

public class ExchangeToBrokerUnitApiGateway implements IExchangeToBrokerUnitApiGateway {

    private final IExchangeServiceRegistry exchangeServiceRegistry;

    @Inject
    public ExchangeToBrokerUnitApiGateway(IExchangeServiceRegistry exchangeServiceRegistry) {
        this.exchangeServiceRegistry = exchangeServiceRegistry;
    }

    @Override
    public BooleanResultMessages submitSettlementResults(List<SettlementResult> exchangeSettlementResults) {
        List<BooleanResultMessages> listOfResultMessages = new ArrayList<>();

        List<emSeco.brokerUnit.core.entities.settlementResult.SettlementResult>
                brokerSettlementResults = mapExchangeSettlementResultToBrokerSettlementResult(exchangeSettlementResults);

        List<emSeco.brokerUnit.core.entities.settlementResult.SettlementResult> similarDestinations =
                brokerSettlementResults.stream().filter(settlementResult ->
                        settlementResult.getTrade().getBuySide().getRoutingInformation().getBrokerId() ==
                                settlementResult.getTrade().getSellSide().getRoutingInformation().getBrokerId()).
                        collect(Collectors.toList());

        List<emSeco.brokerUnit.core.entities.settlementResult.SettlementResult> differentDestinations =
                brokerSettlementResults.stream().filter(settlementResult ->
                        settlementResult.getTrade().getBuySide().getRoutingInformation().getBrokerId() !=
                                settlementResult.getTrade().getSellSide().getRoutingInformation().getBrokerId()).
                        collect(Collectors.toList());


        similarDestinations.stream().collect(Collectors.
                groupingBy(tr -> tr.getTrade().getBuySide().getRoutingInformation().getBrokerId()))
                .forEach((brokerId, groupedSettlementResult) -> {
                    IBroker sellSideBroker = exchangeServiceRegistry.getBrokers().stream().
                            filter(bkr -> bkr.getBrokerId() == brokerId)
                            .findAny().orElse(null);

                    if (sellSideBroker == null) {
                        throw new ServiceRegistryMalfunctionException
                                ("Exchange's service registry malfunctions!");
                    }

                    listOfResultMessages.add
                            (sellSideBroker.submitSettlementResults_API(groupedSettlementResult));
                });

        differentDestinations.stream().collect(Collectors.
                groupingBy(tr -> tr.getTrade().getBuySide().getRoutingInformation().getBrokerId()))
                .forEach((brokerId, groupedSettlementResult) -> {
                    IBroker sellSideBroker = exchangeServiceRegistry.getBrokers().stream().
                            filter(bkr -> bkr.getBrokerId() == brokerId)
                            .findAny().orElse(null);
                    if (sellSideBroker == null) {
                        throw new ServiceRegistryMalfunctionException
                                ("Exchange's service registry malfunctions!");
                    }

                    listOfResultMessages.add(
                            sellSideBroker.submitSettlementResults_API(groupedSettlementResult));
                });

        differentDestinations.stream().collect(Collectors.
                groupingBy(tr -> tr.getTrade().getSellSide().getRoutingInformation().getBrokerId()))
                .forEach((brokerId, groupedSettlementResult) -> {
                    IBroker sellSideBroker = exchangeServiceRegistry.getBrokers().stream().
                            filter(bkr -> bkr.getBrokerId() == brokerId)
                            .findAny().orElse(null);
                    if (sellSideBroker == null) {
                        throw new ServiceRegistryMalfunctionException
                                ("Exchange's service registry malfunctions!");
                    }
                    listOfResultMessages.add(
                            sellSideBroker.submitSettlementResults_API(groupedSettlementResult));
                });

        return aggregateListOfResultMessages(listOfResultMessages);
    }


    @Override
    public BooleanResultMessages submitTrades(List<Trade> exchangeTrades) {
        List<BooleanResultMessages> listOfResultMessages = new ArrayList<>();

        List<emSeco.brokerUnit.core.entities.trade.Trade> brokerTrades =
                mapExchangeTradeToBrokerTrade(exchangeTrades);

        List<emSeco.brokerUnit.core.entities.trade.Trade> tradesWithSameBrokers =
                brokerTrades.stream().filter(trade ->
                        trade.getBuySide().getRoutingInformation().getBrokerId() ==
                                trade.getSellSide().getRoutingInformation().getBrokerId()).
                        collect(Collectors.toList());

        List<emSeco.brokerUnit.core.entities.trade.Trade> tradesWithDifferentBrokers =
                brokerTrades.stream().filter(trade ->
                        trade.getBuySide().getRoutingInformation().getBrokerId() !=
                                trade.getSellSide().getRoutingInformation().getBrokerId()).
                        collect(Collectors.toList());


        tradesWithSameBrokers.stream().collect(Collectors.
                groupingBy(trade -> trade.getBuySide().getRoutingInformation().getBrokerId()))
                .forEach((brokerId, groupedTrades) -> {
                    IBroker broker = exchangeServiceRegistry.getBrokers().stream().
                            filter(bkr -> bkr.getBrokerId() == brokerId)
                            .findAny().orElse(null);

                    if (broker == null) {
                        throw new ServiceRegistryMalfunctionException
                                ("Exchange's service registry malfunctions!");
                    }

                    listOfResultMessages.add(broker.submitTrades_API(groupedTrades));
                });

        tradesWithDifferentBrokers.stream().collect(Collectors.
                groupingBy(tr -> tr.getBuySide().getRoutingInformation().getBrokerId()))
                .forEach((brokerId, groupedTrades) -> {
                    IBroker buySideBroker = exchangeServiceRegistry.getBrokers().stream().
                            filter(bkr -> bkr.getBrokerId() == brokerId)
                            .findAny().orElse(null);

                    if (buySideBroker == null) {
                        throw new ServiceRegistryMalfunctionException
                                ("Exchange's service registry malfunctions!");
                    }

                    listOfResultMessages.add(buySideBroker.submitTrades_API(groupedTrades));
                });

        tradesWithDifferentBrokers.stream().collect(Collectors.
                groupingBy(tr -> tr.getSellSide().getRoutingInformation().getBrokerId()))
                .forEach((brokerId, groupedTrades) -> {
                    IBroker sellSideBroker = exchangeServiceRegistry.getBrokers().stream().
                            filter(bkr -> bkr.getBrokerId() == brokerId)
                            .findAny().orElse(null);

                    if (sellSideBroker == null) {
                        throw new ServiceRegistryMalfunctionException
                                ("Exchange's service registry malfunctions!");
                    }

                    listOfResultMessages.add(sellSideBroker.submitTrades_API(groupedTrades));
                });

        return aggregateListOfResultMessages(listOfResultMessages);
    }
}
