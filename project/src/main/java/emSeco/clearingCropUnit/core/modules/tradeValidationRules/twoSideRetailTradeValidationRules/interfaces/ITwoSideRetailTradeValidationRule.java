package emSeco.clearingCropUnit.core.modules.tradeValidationRules.twoSideRetailTradeValidationRules.interfaces;

import emSeco.clearingCropUnit.core.entities.trade.Trade;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;


public interface ITwoSideRetailTradeValidationRule {
    BooleanResultMessage checkRule(Trade trade);
}
