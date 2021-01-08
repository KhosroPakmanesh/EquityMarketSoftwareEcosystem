package emSeco.clearingCropUnit.core.modules.tradeValidationRules.twoSideInstitutionalTradeValidationRules.interfaces;

import emSeco.clearingCropUnit.core.entities.trade.Trade;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;


public interface ITwoSideInstitutionalTradeValidationRule {
    BooleanResultMessage checkRule(Trade trade);
}
