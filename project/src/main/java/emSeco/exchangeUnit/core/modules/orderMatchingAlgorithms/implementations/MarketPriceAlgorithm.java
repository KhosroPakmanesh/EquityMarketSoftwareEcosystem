//Reference:
//https://github.com/Apress/practical-.net-for-financial-markets/blob/master/CodeExample/Chpt2/Framework/EquityMatchingLogic.cs

package emSeco.exchangeUnit.core.modules.orderMatchingAlgorithms.implementations;

//#if MarketPriceOrderAlgorithm
import com.google.inject.Inject;
import emSeco.exchangeUnit.core.entities.exchangeUnitInfo.ExchangeUnitInfo;
import emSeco.exchangeUnit.core.entities.order.Order;
import emSeco.exchangeUnit.core.entities.orderBook.OrderArrayList;
import emSeco.exchangeUnit.core.entities.orderBook.OrderTypeContainer;
import emSeco.exchangeUnit.core.entities.shared.Side;
import emSeco.exchangeUnit.core.entities.trade.Trade;
import emSeco.exchangeUnit.core.modules.orderMatchingAlgorithms.interfaces.IOrderMatchingAlgorithm;
import emSeco.exchangeUnit.infrastructure.services.databases.exchangeUnitRepositories.implementations.ExchangeUnitRepositories;
import emSeco.shared.exceptions.DataPersistenceMalfunctionException;
import emSeco.shared.services.dateTimeService.interfaces.IDateTimeService;
import emSeco.shared.services.uuidGenerator.interfaces.IUUIDGenerator;

import java.util.ArrayList;
import java.util.List;

import static emSeco.exchangeUnit.core.helpers.ExchangeToExchangeEntitiesMapper.*;

public class MarketPriceAlgorithm implements IOrderMatchingAlgorithm {

    private final ExchangeUnitRepositories exchangeUnitRepositories;
    private final IDateTimeService dateTimeService;
    private final IUUIDGenerator uuidGenerator;

    @Inject
    public MarketPriceAlgorithm(ExchangeUnitRepositories exchangeUnitRepositories,
                                IDateTimeService dateTimeService,
                                IUUIDGenerator uuidGenerator) {
        this.exchangeUnitRepositories = exchangeUnitRepositories;
        this.dateTimeService = dateTimeService;
        this.uuidGenerator = uuidGenerator;
    }

    public String getOrderTypeMatchingAlgorithmName() {
        return "MP";
    }

    public String getParentOrderTypeMatchingAlgorithmName() {
        return "LIM";
    }

    public List<Trade> matchBuyLogic(Order inProcessBuyOrder, OrderTypeContainer orderTypeContainer) {
        ExchangeUnitInfo exchangeUnitInfo = exchangeUnitRepositories.getExchangeUnitInfoRepository().get();
        if (exchangeUnitInfo == null) {
            throw new DataPersistenceMalfunctionException
                    ("Exchange's data persistence mechanism has not stored the data!");
        }

        List<Trade> trades = new ArrayList<>();
        OrderArrayList storedSellOrders = orderTypeContainer.getSellOrders();

        inProcessBuyOrder.getTerm().setPrice(storedSellOrders.getMarketPrice());
        for (Order storedSellOrder : storedSellOrders) {
            if (inProcessBuyOrder.getTerm().getQuantity() > 0) {

                Side buySide = mapFromOrdersToBuyTradeSideForMainBuyOrder
                        (inProcessBuyOrder, storedSellOrder);

                Side sellSide = mapFromOrdersToSellTradeSideForMainBuyOrder
                        (inProcessBuyOrder, storedSellOrder);

                trades.add(new Trade
                        (exchangeUnitInfo.getExchangeId(),
                                uuidGenerator.generateUUID(),
                                dateTimeService.getDateTime(),
                                buySide, sellSide));

                int quantity = storedSellOrder.getTerm().getQuantity();
                storedSellOrder.getTerm().setQuantity(storedSellOrder.getTerm().getQuantity()
                        - inProcessBuyOrder.getTerm().getQuantity());
                inProcessBuyOrder.getTerm().setQuantity(inProcessBuyOrder.getTerm().getQuantity() - quantity);
            } else {
                break;
            }
        }

        return trades;
    }

    public List<Trade> matchSellLogic(Order inProcessSellOrder, OrderTypeContainer orderTypeContainer) {
        ExchangeUnitInfo exchangeUnitInfo = exchangeUnitRepositories.getExchangeUnitInfoRepository().get();
        if (exchangeUnitInfo == null) {
            throw new DataPersistenceMalfunctionException
                    ("Exchange's data persistence mechanism has not stored the data!");
        }

        List<Trade> trades = new ArrayList<>();
        OrderArrayList storedBuyOrders = orderTypeContainer.getBuyOrders();

        inProcessSellOrder.getTerm().setPrice(storedBuyOrders.getMarketPrice());
        for (Order storedBuyOrder : storedBuyOrders) {
            if (inProcessSellOrder.getTerm().getQuantity() > 0) {

                Side buySide = mapFromOrdersToBuyTradeSideForMainSellOrder
                        (inProcessSellOrder, storedBuyOrder);

                Side sellSide = mapFromOrdersToSellTradeSideForMainSellOrder
                        (inProcessSellOrder, storedBuyOrder);

                trades.add(new Trade
                        (exchangeUnitInfo.getExchangeId(),
                                uuidGenerator.generateUUID(),
                                dateTimeService.getDateTime(),
                                buySide, sellSide));

                int quantity = storedBuyOrder.getTerm().getQuantity();
                storedBuyOrder.getTerm().setQuantity(storedBuyOrder.getTerm().getQuantity() -
                        inProcessSellOrder.getTerm().getQuantity());
                inProcessSellOrder.getTerm().setQuantity(inProcessSellOrder.getTerm().getQuantity() - quantity);
            } else {
                break;
            }
        }

        return trades;
    }
}
//#endif
