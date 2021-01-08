package emSeco.clearingCropUnit.core.modules.tradeClearingRules.twoSideInstitutionalTradeClearingRules.implementations;

//#if TwoSideInstitutionalTradingInformationEquality
import emSeco.clearingCropUnit.core.entities.trade.Trade;
import emSeco.clearingCropUnit.core.modules.tradeClearingRules.twoSideInstitutionalTradeClearingRules.interfaces.ITwoSideInstitutionalTradeClearingRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TwoSideInstitutionalTradingInformationEquality implements ITwoSideInstitutionalTradeClearingRule {
    @Override
    public BooleanResultMessage checkRule(List<Trade> trades) {
        List<Trade> buySideTrades = trades.stream().
                filter(trade -> trade.getBuySide().getAllocationDetailInformation().
                        getAllocationDetailBlockId() != null).
                collect(Collectors.toList());

        List<Trade> sellSideTrades = trades.stream().
                filter(trade -> trade.getSellSide().getAllocationDetailInformation().
                        getAllocationDetailBlockId() != null).
                collect(Collectors.toList());

        List<UUID> buySideBuyRegisteredCode = buySideTrades.stream().
                map(trade -> trade.getBuySide().getTradingInformation().getRegisteredCode()).
                distinct().collect(Collectors.toList());

        List<UUID> sellSideBuyRegisteredCode = sellSideTrades.stream().
                map(trade -> trade.getBuySide().getTradingInformation().getRegisteredCode()).
                distinct().collect(Collectors.toList());

        List<UUID> buySideSellRegisteredCode = buySideTrades.stream().
                map(trade -> trade.getSellSide().getTradingInformation().getRegisteredCode()).
                distinct().collect(Collectors.toList());

        List<UUID> sellSideSellRegisteredCode = sellSideTrades.stream().
                map(trade -> trade.getSellSide().getTradingInformation().getRegisteredCode()).
                distinct().collect(Collectors.toList());

        if (!(buySideBuyRegisteredCode.size() == 1 &&
                sellSideBuyRegisteredCode.size() == 1 &&
                buySideSellRegisteredCode.size() == 1 &&
                sellSideSellRegisteredCode.size() == 1 &&
                buySideBuyRegisteredCode.stream().findAny().equals(
                        sellSideBuyRegisteredCode.stream().findAny()) &&
                buySideSellRegisteredCode.stream().findAny().equals(
                        sellSideSellRegisteredCode.stream().findAny())))
        {
            return new BooleanResultMessage(false,
                    OperationMessage.Create("The registered codes of each side" +
                            " of a two-side institutional trade should be equal"));
        }

        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }
}
//#endif
