package emSeco.exchangeUnit.core.modules.orderMatchingAlgorithms.implementations;

//#if FakeStopLossAlgorithm 
//@import stp.exchangeUnit.core.entities.order.Order;
//@import stp.exchangeUnit.core.entities.orderBook.OrderTypeContainer;
//@import stp.exchangeUnit.core.entities.trade.Trade;
//@import stp.exchangeUnit.core.modules.orderMatchingAlgorithms.interfaces.IOrderMatchingAlgorithm;
//@
//@import java.util.ArrayList;
//@import java.util.List;
//@
//@public class FakeStopLossAlgorithm implements IOrderMatchingAlgorithm {
//@    @Override
//@    public String getOrderTypeMatchingAlgorithmName() {
//@        return "SL";
//@    }
//@
//@    @Override
//@    public String getParentOrderTypeMatchingAlgorithmName() {
//@        return "SL";
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
