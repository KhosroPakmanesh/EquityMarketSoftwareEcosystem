package emSeco.clearingCropUnit.core.modules.tradeClearingRulesEvaluator.interfaces;

import emSeco.clearingCropUnit.core.entities.trade.Trade;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;

import java.util.List;

public interface ITradeClearingRulesEvaluator {
    List<BooleanResultMessage> EvaluateTwoSideInstitutionalTrades(List<Trade> trades);
    List<BooleanResultMessage> EvaluateOneSideInstitutionalTrades(List<Trade> trades);
    List<BooleanResultMessage> EvaluateTwoSideRetailTrades(List<Trade> trades);
}
