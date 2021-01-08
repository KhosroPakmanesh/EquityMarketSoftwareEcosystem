package emSeco.exchangeUnit.core.modules.exchange.interfaces;

import emSeco.exchangeUnit.core.entities.order.Order;
import emSeco.exchangeUnit.core.entities.settlementResult.SettlementResult;
import emSeco.shared.architecturalConstructs.BooleanResultMessages;

import java.util.List;
import java.util.UUID;

public interface IExchange {
    void setExchangeInfo(UUID exchangeId);
    UUID getExchangeId();
    BooleanResultMessages submitOrder_API(Order order);
    BooleanResultMessages submitSettlementResults_API(List<SettlementResult> settlementResults);
    List<BooleanResultMessages> processOrders_REC();
    BooleanResultMessages sendSettlementResults_REC();
    BooleanResultMessages sendTrades_REC();
}
