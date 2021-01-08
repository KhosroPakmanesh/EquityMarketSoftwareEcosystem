package emSeco.exchangeUnit.core.modules.exchange.implementations;

import com.google.inject.Inject;
import emSeco.exchangeUnit.core.entities.exchangeUnitInfo.ExchangeUnitInfo;
import emSeco.exchangeUnit.core.entities.order.Order;
import emSeco.exchangeUnit.core.entities.shared.InitiatorType;
import emSeco.exchangeUnit.core.entities.trade.Trade;
import emSeco.exchangeUnit.core.entities.settlementResult.SettlementResult;
import emSeco.exchangeUnit.core.modules.exchange.interfaces.IExchange;
import emSeco.exchangeUnit.core.modules.orderFactory.interfaces.IOrderFactory;
import emSeco.exchangeUnit.core.modules.orderFactory.models.ConstructOrderOutputClass;
import emSeco.exchangeUnit.core.modules.orderProcessor.interfaces.IOrderProcessor;
import emSeco.exchangeUnit.core.services.infrastructureServices.databases.exchangeUnitRepositories.interfaces.IExchangeUnitRepositories;
import emSeco.exchangeUnit.core.services.infrastructureServices.gateways.exchangeUnitApiGateways.interfaces.IExchangeUnitApiGateways;
import emSeco.shared.architecturalConstructs.BooleanResultMessages;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static emSeco.shared.architecturalConstructs.BooleanResultMessages.aggregateListOfResultMessage;

public class Exchange implements IExchange {
    private UUID exchangeId;
    private final IExchangeUnitRepositories exchangeUnitRepositories;
    private final IOrderFactory orderFactory;
    private final IExchangeUnitApiGateways exchangeUnitApiGateways;
    private final IOrderProcessor orderProcessor;

    @Inject
    public Exchange(IExchangeUnitRepositories exchangeUnitRepositories,
                    IExchangeUnitApiGateways exchangeUnitApiGateways,
                    IOrderProcessor orderProcessor,
                    IOrderFactory orderFactory) {
        this.exchangeUnitApiGateways = exchangeUnitApiGateways;
        this.orderProcessor = orderProcessor;
        this.exchangeUnitRepositories = exchangeUnitRepositories;
        this.orderFactory = orderFactory;
    }

    public void setExchangeInfo(UUID exchangeId) {
        this.exchangeId = exchangeId;
        exchangeUnitRepositories.getExchangeUnitInfoRepository().
                add(new ExchangeUnitInfo(exchangeId));
    }

    @Override
    public UUID getExchangeId() {
        return exchangeId;
    }

    @Override
    public BooleanResultMessages submitSettlementResults_API(List<SettlementResult> settlementResults) {
        //Here we could have a factory to reconstruct and validate trade results.
        exchangeUnitRepositories.getSettlementResultRepository().add(settlementResults);
        return new BooleanResultMessages(true, new ArrayList<>());
    }

    @Override
    public BooleanResultMessages submitOrder_API(Order order) {
        ConstructOrderOutputClass constructRetailOrderOutputClass =
                orderFactory.constructOrder(order);

        if (constructRetailOrderOutputClass.hasErrors()) {
            return aggregateListOfResultMessage
                    (constructRetailOrderOutputClass.getOrderConstructionResultMessages());
        }

        orderProcessor.submitOrder(order);
        return new BooleanResultMessages(true, new ArrayList<>());
    }

    @Override
    public List<BooleanResultMessages> processOrders_REC() {
        List<Trade> trades = orderProcessor.processOrders();

        List<Trade> retailTrades = trades.stream().filter(
                trade -> trade.getInitiatorType() == InitiatorType.retail).collect(Collectors.toList());

        List<BooleanResultMessages> tradesSubmissionResultMessages =
                exchangeUnitApiGateways.getExchangeToClearingCorpUnitApiGateway().
                        submitRetailTrades(retailTrades);

        if (tradesSubmissionResultMessages.size() == 0) {
            exchangeUnitRepositories.getTradeRepository().add(trades);
        }

        return tradesSubmissionResultMessages;
    }

    @Override
    public BooleanResultMessages sendSettlementResults_REC() {
        List<SettlementResult> settlementResults = exchangeUnitRepositories.getSettlementResultRepository().get();
        return exchangeUnitApiGateways.getExchangeToBrokerUnitApiGateway().submitSettlementResults(settlementResults);
    }

    @Override
    public BooleanResultMessages sendTrades_REC() {
        List<Trade> trades = exchangeUnitRepositories.getTradeRepository().get();
        return exchangeUnitApiGateways.getExchangeToBrokerUnitApiGateway().submitTrades(trades);
    }
}
