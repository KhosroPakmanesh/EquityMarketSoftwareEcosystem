package emSeco.clearingCropUnit.core.modules.tradeClearingRules.twoSideInstitutionalTradeClearingRules.implementations;

//#if TwoSideInstitutionalAllocationDetailInformationEquality
import emSeco.clearingCropUnit.core.entities.trade.Trade;
import emSeco.clearingCropUnit.core.modules.tradeClearingRules.twoSideInstitutionalTradeClearingRules.interfaces.ITwoSideInstitutionalTradeClearingRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TwoSideInstitutionalAllocationDetailInformationEquality implements ITwoSideInstitutionalTradeClearingRule {
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

        List<UUID> buySideBuyAllocationDetailBlockIds = buySideTrades.stream().
                map(trade -> trade.getBuySide().getAllocationDetailInformation().
                        getAllocationDetailBlockId()).
                distinct().collect(Collectors.toList());

        List<UUID> sellSideBuyAllocationDetailBlockIds = sellSideTrades.stream().
                map(trade -> trade.getBuySide().getAllocationDetailInformation().
                        getAllocationDetailBlockId()).
                distinct().collect(Collectors.toList());

        List<UUID> buySideSellAllocationDetailBlockIds = buySideTrades.stream().
                map(trade -> trade.getSellSide().getAllocationDetailInformation().
                        getAllocationDetailBlockId()).
                distinct().collect(Collectors.toList());

        List<UUID> sellSideSellAllocationDetailBlockIds = sellSideTrades.stream().
                map(trade -> trade.getSellSide().getAllocationDetailInformation().
                        getAllocationDetailBlockId()).
                distinct().collect(Collectors.toList());

        if (!(buySideBuyAllocationDetailBlockIds.size() == 1 &&
                sellSideBuyAllocationDetailBlockIds.size() == 1 &&
                buySideSellAllocationDetailBlockIds.size() == 1 &&
                sellSideSellAllocationDetailBlockIds.size() == 1 &&
                buySideBuyAllocationDetailBlockIds.stream().findAny().
                        equals(sellSideBuyAllocationDetailBlockIds.stream().findAny()) &&
                buySideSellAllocationDetailBlockIds.stream().findAny().
                        equals(sellSideSellAllocationDetailBlockIds.stream().findAny())
        )) {
            return new BooleanResultMessage(false,
                    OperationMessage.Create("The allocationDetailBlockIds of each side" +
                            " of a two-side institutional trade should be equal"));
        }

        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }
}
//#endif
