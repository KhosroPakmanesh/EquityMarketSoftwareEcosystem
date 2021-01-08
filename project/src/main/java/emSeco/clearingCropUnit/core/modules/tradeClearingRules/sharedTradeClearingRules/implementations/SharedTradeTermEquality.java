package emSeco.clearingCropUnit.core.modules.tradeClearingRules.sharedTradeClearingRules.implementations;

//#if SharedTradeTermEquality
import emSeco.clearingCropUnit.core.entities.trade.Trade;
import emSeco.clearingCropUnit.core.modules.tradeClearingRules.sharedTradeClearingRules.interfaces.ISharedTradeClearingRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;

import java.util.List;
import java.util.stream.Collectors;

public class SharedTradeTermEquality implements ISharedTradeClearingRule {
    @Override
    public BooleanResultMessage checkRule(List<Trade> trades) {
        List<String> buySideBuyInstruments = trades.stream().
                map(trade -> trade.getBuySide().getTerm().getInstrumentName()).
                distinct().collect(Collectors.toList());

        List<String> sellSideBuyInstruments = trades.stream().
                map(trade -> trade.getBuySide().getTerm().getInstrumentName()).
                distinct().collect(Collectors.toList());

        List<String> buySideSellInstruments = trades.stream().
                map(trade -> trade.getSellSide().getTerm().getInstrumentName()).
                distinct().collect(Collectors.toList());

        List<String> sellSideSellInstruments = trades.stream().
                map(trade -> trade.getSellSide().getTerm().getInstrumentName()).
                distinct().collect(Collectors.toList());

        if (!(buySideBuyInstruments.size() == 1 && sellSideBuyInstruments.size() == 1 &&
                buySideSellInstruments.size() == 1 && sellSideSellInstruments.size() == 1 &&
                buySideBuyInstruments.stream().findAny().
                        equals(sellSideBuyInstruments.stream().findAny()) &&
                buySideSellInstruments.stream().findAny().
                        equals(sellSideSellInstruments.stream().findAny()))) {
            return new BooleanResultMessage(false
                    , OperationMessage.Create("The instrument names of both sides of the trades are not equal!"));
        }


        List<Double> buySideBuyPrices = trades.stream().
                map(trade -> trade.getBuySide().getTerm().getPrice()).
                distinct().collect(Collectors.toList());

        List<Double> sellSideBuyPrices = trades.stream().
                map(trade -> trade.getBuySide().getTerm().getPrice()).
                distinct().collect(Collectors.toList());

        List<Double> buySideSellPrices = trades.stream().
                map(trade -> trade.getSellSide().getTerm().getPrice()).
                distinct().collect(Collectors.toList());

        List<Double> sellSideSellPrices = trades.stream().
                map(trade -> trade.getSellSide().getTerm().getPrice()).
                distinct().collect(Collectors.toList());

        if (!(buySideBuyPrices.size() == 1 && sellSideBuyPrices.size() == 1 &&
                buySideSellPrices.size() == 1 && sellSideSellPrices.size() == 1 &&
                buySideBuyPrices.stream().findAny().
                        equals(sellSideBuyPrices.stream().findAny()) &&
                buySideSellPrices.stream().findAny().
                        equals(sellSideSellPrices.stream().findAny()))) {
            return new BooleanResultMessage(false
                    , OperationMessage.Create("The prices of both sides of the trades are not equal!"));
        }


        int buySideBuyQuantity = trades.stream().map(trade -> trade.getBuySide()
                .getTerm().getQuantity()).mapToInt(Integer::intValue).sum();

        int sellSideBuyQuantity = trades.stream().map(trade -> trade.getBuySide()
                .getTerm().getQuantity()).mapToInt(Integer::intValue).sum();

        int buySideSellQuantity = trades.stream().map(trade -> trade.getSellSide()
                .getTerm().getQuantity()).mapToInt(Integer::intValue).sum();

        int sellSideSellQuantity = trades.stream().map(trade -> trade.getSellSide()
                .getTerm().getQuantity()).mapToInt(Integer::intValue).sum();

        if (!(buySideBuyQuantity == sellSideBuyQuantity &&
                buySideSellQuantity == sellSideSellQuantity &&
                buySideBuyQuantity == sellSideSellQuantity)) {
            return new BooleanResultMessage(false
                    , OperationMessage.Create("The quantities of both sides of the trade are not equal!"));
        }

        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }
}
//#endif