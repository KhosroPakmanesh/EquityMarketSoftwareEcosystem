package emSeco.clearingCropUnit.core.modules.tradeClearingRules.sharedTradeClearingRules.implementations;

//#if SharedOrderInitiatorsInequality
import emSeco.clearingCropUnit.core.entities.shared.InitiatorType;
import emSeco.clearingCropUnit.core.entities.trade.Trade;
import emSeco.clearingCropUnit.core.modules.tradeClearingRules.sharedTradeClearingRules.interfaces.ISharedTradeClearingRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;


import java.util.List;
import java.util.stream.Collectors;

public class SharedOrderInitiatorsInequality implements ISharedTradeClearingRule {
    @Override
    public BooleanResultMessage checkRule(List<Trade> trades) {
        List<InitiatorType> buySideBuyInitiatorTypes = trades.stream().
                map(trade -> trade.getBuySide().getTradingInformation().getInitiatorType()).
                distinct().collect(Collectors.toList());

        List<InitiatorType> sellSideBuyInitiatorTypes = trades.stream().
                map(trade -> trade.getBuySide().getTradingInformation().getInitiatorType()).
                distinct().collect(Collectors.toList());

        List<InitiatorType> buySideSellInitiatorTypes = trades.stream().
                map(trade -> trade.getSellSide().getTradingInformation().getInitiatorType()).
                distinct().collect(Collectors.toList());

        List<InitiatorType> sellSideSellInitiatorTypes = trades.stream().
                map(trade -> trade.getSellSide().getTradingInformation().getInitiatorType()).
                distinct().collect(Collectors.toList());

        if (!(buySideBuyInitiatorTypes.size() == 1 && sellSideBuyInitiatorTypes.size() == 1 &&
                buySideSellInitiatorTypes.size() == 1 && sellSideSellInitiatorTypes.size() == 1 &&
                buySideBuyInitiatorTypes.stream().findAny().
                        equals(sellSideBuyInitiatorTypes.stream().findAny()) &&
                buySideSellInitiatorTypes.stream().findAny().
                        equals(sellSideSellInitiatorTypes.stream().findAny()))) {
            return new BooleanResultMessage(false
                    , OperationMessage.Create("The orderIds of both sides of the trades are not equal!"));
        }

        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }
}
//#endif
