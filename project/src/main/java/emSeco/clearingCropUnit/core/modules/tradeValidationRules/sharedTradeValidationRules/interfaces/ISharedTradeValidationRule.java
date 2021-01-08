package emSeco.clearingCropUnit.core.modules.tradeValidationRules.sharedTradeValidationRules.interfaces;

import emSeco.clearingCropUnit.core.entities.trade.Trade;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;

public interface ISharedTradeValidationRule {
    BooleanResultMessage checkRule(Trade trade);
}
