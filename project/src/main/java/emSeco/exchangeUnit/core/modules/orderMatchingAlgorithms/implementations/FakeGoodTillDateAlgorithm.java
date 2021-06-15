package emSeco.exchangeUnit.core.modules.orderMatchingAlgorithms.implementations;

//#if FakeGoodTillDateAlgorithm
//@import emSeco.exchangeUnit.core.entities.order.Order;
//@import emSeco.exchangeUnit.core.entities.orderBook.OrderTypeContainer;
//@import emSeco.exchangeUnit.core.entities.trade.Trade;
//@import emSeco.exchangeUnit.core.modules.orderMatchingAlgorithms.interfaces.IOrderMatchingAlgorithm;
//@
//@import java.util.ArrayList;
//@import java.util.List;
//@
//@public class FakeGoodTillDateAlgorithm implements IOrderMatchingAlgorithm {
//@    @Override
//@    public String getOrderTypeMatchingAlgorithmName() {
//@        return "GTD";
//@    }
//@
//@    @Override
//@    public String getParentOrderTypeMatchingAlgorithmName() {
//@        return "GTD";
//@    }
//@
//@    @Override
//@    public List<Trade> matchBuyLogic(Order order, OrderTypeContainer orderTypeContainer) {
//@        return new ArrayList<>();
//@    }
//@
//@    @Override
//@    public List<Trade> matchSellLogic(Order order, OrderTypeContainer orderTypeContainer) {
//@        return new ArrayList<>();
//@    }
//@}
//#endif
