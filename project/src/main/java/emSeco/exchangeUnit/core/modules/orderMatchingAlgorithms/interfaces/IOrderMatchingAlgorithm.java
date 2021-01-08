package emSeco.exchangeUnit.core.modules.orderMatchingAlgorithms.interfaces;

import emSeco.exchangeUnit.core.entities.order.Order;
import emSeco.exchangeUnit.core.entities.orderBook.OrderTypeContainer;
import emSeco.exchangeUnit.core.entities.trade.Trade;

import java.util.List;

public interface IOrderMatchingAlgorithm {
    String getOrderTypeMatchingAlgorithmName();
    String getParentOrderTypeMatchingAlgorithmName();
    List<Trade> matchBuyLogic(Order order, OrderTypeContainer orderTypeContainer);
    List<Trade>  matchSellLogic(Order order, OrderTypeContainer orderTypeContainer);
}
