package emSeco.clearingCropUnit.core.modules.tradeClearingRules.twoSideInstitutionalTradeClearingRules.interfaces;


import emSeco.clearingCropUnit.core.entities.trade.Trade;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;

import java.util.List;

public interface ITwoSideInstitutionalTradeClearingRule {
    BooleanResultMessage checkRule(List<Trade> trades);
}
