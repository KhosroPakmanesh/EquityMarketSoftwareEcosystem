package emSeco.clearingCropUnit.core.modules.tradeValidationRules.oneSideInstitutionalTradeValidationRules.interfaces;

import emSeco.clearingCropUnit.core.entities.trade.Trade;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;


public interface IOneSideInstitutionalTradeValidationRule {
    BooleanResultMessage checkRule(Trade trade);
}
