package emSeco.clearingCropUnit.core.modules.tradeValidator.interfaces;

import emSeco.clearingCropUnit.core.entities.trade.Trade;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;

import java.util.List;

public interface ITradeValidator {
    List<BooleanResultMessage> validateTwoSideRetailTrade(Trade trade);
    List<BooleanResultMessage> validateInstitutionalTrade(Trade trade);
}
