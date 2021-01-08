package emSeco.clearingCropUnit.core.modules.tradeFactory.models;

import emSeco.clearingCropUnit.core.entities.trade.Trade;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;

import java.util.List;

public class ConstructTradeOutputClass {

    private final List<BooleanResultMessage> constructSettlementResultMessages;
    private final Trade trade;

    public ConstructTradeOutputClass(List<BooleanResultMessage> constructSettlementResultMessages, Trade trade) {
        this.constructSettlementResultMessages = constructSettlementResultMessages;
        this.trade = trade;
    }

    public List<BooleanResultMessage> getConstructSettlementResultMessages() {
        return constructSettlementResultMessages;
    }

    public Trade getTrade() {
        return trade;
    }
}
