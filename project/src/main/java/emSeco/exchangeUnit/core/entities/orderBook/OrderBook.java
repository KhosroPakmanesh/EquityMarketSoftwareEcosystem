package emSeco.exchangeUnit.core.entities.orderBook;

import com.google.inject.Inject;
import emSeco.exchangeUnit.core.entities.order.Order;
import emSeco.exchangeUnit.core.entities.shared.SideName;
import emSeco.exchangeUnit.core.entities.trade.Trade;
import emSeco.exchangeUnit.core.modules.orderMatchingAlgorithms.interfaces.IOrderMatchingAlgorithm;
import emSeco.shared.exceptions.InconsistentProductGenerationException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;


public class OrderBook {
    private final Set<IOrderMatchingAlgorithm> orderTypeMatchingAlgorithms;
    private final Comparator<Order> orderPrecedenceRule;
    private final List<InstrumentContainer> instrumentContainers;

    @Inject
    public OrderBook(Set<IOrderMatchingAlgorithm> orderTypeMatchingAlgorithms
            , Comparator<Order> orderPrecedenceRule) {
        this.orderTypeMatchingAlgorithms = orderTypeMatchingAlgorithms;
        this.orderPrecedenceRule = orderPrecedenceRule;
        this.instrumentContainers = new ArrayList<>();
    }

    public List<Trade> makeTrade(Order order) {
        InstrumentContainer instrumentContainer =
                getInstrumentContainer(order.getTerm().getInstrumentName());

        if (instrumentContainer == null){
            createFirstInstrumentContainer(order);
            return new ArrayList<>();
        }

        IOrderMatchingAlgorithm orderTypeMatchingAlgorithm = getOrderTypeMatchingAlgorithm(order);
        String parentOrderTypeName= orderTypeMatchingAlgorithm.getParentOrderTypeMatchingAlgorithmName();
        OrderTypeContainer orderTypeContainer = instrumentContainer.getOrderTypeContainer(parentOrderTypeName);


        if (orderTypeContainer == null){
            orderTypeContainer =instrumentContainer.createEmptyOrderContainer(order);
        }

        List<Trade> trades= MatchOrders(order, orderTypeMatchingAlgorithm, orderTypeContainer);

        if (order.hasLeftOver()) {
            storeOrder(orderTypeContainer,order);
        }

        cleanUpInstrumentContainer(orderTypeContainer);

        return trades;
    }

    private List<Trade> MatchOrders
            (Order order, IOrderMatchingAlgorithm orderTypeMatchingAlgorithm
                    , OrderTypeContainer orderTypeContainer){

        List<Trade> trades= new ArrayList<>();

        if ( order.getTradingInformation().getSideName() == SideName.buy ){
            trades.addAll(orderTypeMatchingAlgorithm.matchBuyLogic
                    (order, orderTypeContainer));
        }
        else {
            trades.addAll(orderTypeMatchingAlgorithm.matchSellLogic
                    (order, orderTypeContainer));
        }

        return trades;
    }

    private InstrumentContainer getInstrumentContainer(String instrumentName) {
        return instrumentContainers.stream().filter(ic ->
                        ic.getInstrumentName().equals(instrumentName)).findAny().orElse(null);
    }

    private IOrderMatchingAlgorithm getOrderTypeMatchingAlgorithm(Order order){

        IOrderMatchingAlgorithm orderTypeMatchingAlgorithm =
                orderTypeMatchingAlgorithms.stream().filter(otma ->
                        otma.getOrderTypeMatchingAlgorithmName().
                                equals(order.getTradingInformation().getOrderType())).findAny().orElse(null);

        if (orderTypeMatchingAlgorithm == null)
        {
            throw new InconsistentProductGenerationException("");
        }

        return orderTypeMatchingAlgorithm;
    }

    private void createFirstInstrumentContainer(Order order) {
        InstrumentContainer newInstrumentContainer = new InstrumentContainer(order);
        instrumentContainers.add(newInstrumentContainer);
    }

    private void storeOrder(OrderTypeContainer orderTypeContainer,Order order) {
        if (order.getTradingInformation().getSideName() == SideName.buy)
        {
            orderTypeContainer.getBuyOrders().add(order,orderPrecedenceRule);
        }
        else {
            orderTypeContainer.getSellOrders().add(order,orderPrecedenceRule);
        }
    }

    private void cleanUpInstrumentContainer(OrderTypeContainer orderTypeContainer) {
            orderTypeContainer.getSellOrders().removeIf(order -> order.getTerm().getQuantity() <= 0);
            orderTypeContainer.getBuyOrders().removeIf(order -> order.getTerm().getQuantity() <= 0);
    }
}
