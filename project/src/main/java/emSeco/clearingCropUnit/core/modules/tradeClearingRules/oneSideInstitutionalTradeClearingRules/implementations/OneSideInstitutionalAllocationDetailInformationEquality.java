package emSeco.clearingCropUnit.core.modules.tradeClearingRules.oneSideInstitutionalTradeClearingRules.implementations;

//#if OneSideInstitutionalAllocationDetailInformationEquality
import emSeco.clearingCropUnit.core.entities.trade.Trade;
import emSeco.clearingCropUnit.core.modules.tradeClearingRules.oneSideInstitutionalTradeClearingRules.interfaces.IOneSideInstitutionalTradeClearingRule;

import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OneSideInstitutionalAllocationDetailInformationEquality implements IOneSideInstitutionalTradeClearingRule {
    @Override
    public BooleanResultMessage checkRule(List<Trade> trades) {
        List<Trade> buySideTrades = trades.stream().
                filter(trade -> trade.getBuySide().isInstitutional()).
                collect(Collectors.toList());

        List<Trade> sellSideTrades = trades.stream().
                filter(trade -> trade.getSellSide().isInstitutional()).
                collect(Collectors.toList());

        List<Trade> institutionalTrades;
        if (buySideTrades.size() > 0 && sellSideTrades.size() == 0) {
            institutionalTrades = buySideTrades;
        } else if (buySideTrades.size() == 0 && sellSideTrades.size() > 0) {
            institutionalTrades = sellSideTrades;
        } else {
            throw new RuntimeException("The module's preconditions are not correct!");
        }

        List<UUID> institutionalSideAllocationDetailBlockIds = institutionalTrades.stream().
                map(trade -> trade.getBuySide().
                        getAllocationDetailInformation().getAllocationDetailBlockId()).
                distinct().collect(Collectors.toList());

        if (institutionalSideAllocationDetailBlockIds.size() > 1) {
            return new BooleanResultMessage(false,
                    OperationMessage.
                            Create("The allocationDetailBlockIds of the" +
                                    " institutional side are not equal!"));
        }

        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }
}
//#endif