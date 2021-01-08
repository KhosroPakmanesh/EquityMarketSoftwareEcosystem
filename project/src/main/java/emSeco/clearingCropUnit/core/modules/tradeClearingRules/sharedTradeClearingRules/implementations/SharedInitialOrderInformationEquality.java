package emSeco.clearingCropUnit.core.modules.tradeClearingRules.sharedTradeClearingRules.implementations;

//#if SharedInitialOrderInformationEquality
import emSeco.clearingCropUnit.core.entities.trade.Trade;
import emSeco.clearingCropUnit.core.modules.tradeClearingRules.sharedTradeClearingRules.interfaces.ISharedTradeClearingRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class SharedInitialOrderInformationEquality implements ISharedTradeClearingRule {
    @Override
    public BooleanResultMessage checkRule(List<Trade> trades) {
        List<UUID> buySideBuyOrderIds = trades.stream().
                map(trade -> trade.getBuySide().getTradingInformation().getInitialOrderId()).
                distinct().collect(Collectors.toList());

        List<UUID> sellSideBuyOrderIds = trades.stream().
                map(trade -> trade.getBuySide().getTradingInformation().getInitialOrderId()).
                distinct().collect(Collectors.toList());

        List<UUID> buySideSellOrderIds = trades.stream().
                map(trade -> trade.getSellSide().getTradingInformation().getInitialOrderId()).
                distinct().collect(Collectors.toList());

        List<UUID> sellSideSellOrderIds = trades.stream().
                map(trade -> trade.getSellSide().getTradingInformation().getInitialOrderId()).
                distinct().collect(Collectors.toList());

        if (!(buySideBuyOrderIds.size() == 1 && sellSideBuyOrderIds.size() == 1 &&
                buySideSellOrderIds.size() == 1 && sellSideSellOrderIds.size() == 1 &&
                buySideBuyOrderIds.stream().findAny().
                        equals(sellSideBuyOrderIds.stream().findAny()) &&
                buySideSellOrderIds.stream().findAny().
                        equals(sellSideSellOrderIds.stream().findAny()))) {
            return new BooleanResultMessage(false
                    , OperationMessage.Create("The orderIds of both sides of the trades are not equal!"));
        }

        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }
}
//#endif