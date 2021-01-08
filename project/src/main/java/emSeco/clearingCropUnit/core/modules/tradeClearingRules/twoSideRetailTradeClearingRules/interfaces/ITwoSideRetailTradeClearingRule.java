package emSeco.clearingCropUnit.core.modules.tradeClearingRules.twoSideRetailTradeClearingRules.interfaces;

import emSeco.clearingCropUnit.core.entities.trade.Trade;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;

import java.util.List;

public interface ITwoSideRetailTradeClearingRule {
    BooleanResultMessage checkRule(List<Trade> trades);
}
