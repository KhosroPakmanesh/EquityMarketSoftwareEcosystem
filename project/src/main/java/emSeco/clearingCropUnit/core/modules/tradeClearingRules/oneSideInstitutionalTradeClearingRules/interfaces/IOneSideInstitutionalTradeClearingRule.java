package emSeco.clearingCropUnit.core.modules.tradeClearingRules.oneSideInstitutionalTradeClearingRules.interfaces;

import emSeco.clearingCropUnit.core.entities.trade.Trade;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;


import java.util.List;

public interface IOneSideInstitutionalTradeClearingRule {
    BooleanResultMessage checkRule(List<Trade> trades);
}
