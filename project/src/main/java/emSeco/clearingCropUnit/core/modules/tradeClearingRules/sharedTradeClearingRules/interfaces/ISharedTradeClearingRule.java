package emSeco.clearingCropUnit.core.modules.tradeClearingRules.sharedTradeClearingRules.interfaces;

import emSeco.clearingCropUnit.core.entities.trade.Trade;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;

import java.util.List;

public interface ISharedTradeClearingRule {
    BooleanResultMessage checkRule(List<Trade> trades);
}
