package emSeco.clearingCropUnit.core.modules.tradeClearingRulesEvaluator.implementations;

import com.google.inject.Inject;
import emSeco.clearingCropUnit.core.entities.trade.Trade;
import emSeco.clearingCropUnit.core.modules.tradeClearingRules.oneSideInstitutionalTradeClearingRules.interfaces.IOneSideInstitutionalTradeClearingRule;
import emSeco.clearingCropUnit.core.modules.tradeClearingRules.sharedTradeClearingRules.interfaces.ISharedTradeClearingRule;
import emSeco.clearingCropUnit.core.modules.tradeClearingRules.twoSideInstitutionalTradeClearingRules.interfaces.ITwoSideInstitutionalTradeClearingRule;
import emSeco.clearingCropUnit.core.modules.tradeClearingRules.twoSideRetailTradeClearingRules.interfaces.ITwoSideRetailTradeClearingRule;
import emSeco.clearingCropUnit.core.modules.tradeClearingRulesEvaluator.interfaces.ITradeClearingRulesEvaluator;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TradeClearingRulesEvaluator implements ITradeClearingRulesEvaluator {
    private final Set<ITwoSideInstitutionalTradeClearingRule> twoSideInstitutionalTradeClearingRules;
    private final Set<IOneSideInstitutionalTradeClearingRule> oneSideInstitutionalTradeClearingRules;
    private final Set<ITwoSideRetailTradeClearingRule> twoSideRetailTradeClearingRules;
    private final Set<ISharedTradeClearingRule> sharedTradeClearingRules;

    @Inject
    public TradeClearingRulesEvaluator
            (Set<ITwoSideInstitutionalTradeClearingRule> twoSideInstitutionalTradeClearingRules,
             Set<IOneSideInstitutionalTradeClearingRule> oneSideInstitutionalTradeClearingRules,
             Set<ITwoSideRetailTradeClearingRule> twoSideRetailTradeClearingRules,
             Set<ISharedTradeClearingRule> sharedTradeClearingRules) {
        this.twoSideInstitutionalTradeClearingRules = twoSideInstitutionalTradeClearingRules;
        this.oneSideInstitutionalTradeClearingRules = oneSideInstitutionalTradeClearingRules;
        this.twoSideRetailTradeClearingRules = twoSideRetailTradeClearingRules;
        this.sharedTradeClearingRules = sharedTradeClearingRules;
    }

    public List<BooleanResultMessage> EvaluateTwoSideInstitutionalTrades(List<Trade> trades) {
        List<BooleanResultMessage> resultMessages = new ArrayList<>();

        for (ISharedTradeClearingRule sharedTradeClearingRule :
                sharedTradeClearingRules) {
            BooleanResultMessage evaluationResultMessage =
                    sharedTradeClearingRule.checkRule(trades);

            if (!evaluationResultMessage.getOperationResult()) {
                resultMessages.add(evaluationResultMessage);
            }
        }

        for (ITwoSideInstitutionalTradeClearingRule twoSideInstitutionalTradeClearingRule :
                twoSideInstitutionalTradeClearingRules) {
            BooleanResultMessage evaluationResultMessage =
                    twoSideInstitutionalTradeClearingRule.checkRule(trades);

            if (!evaluationResultMessage.getOperationResult()) {
                resultMessages.add(evaluationResultMessage);
            }
        }

        return resultMessages;
    }

    @Override
    public List<BooleanResultMessage> EvaluateOneSideInstitutionalTrades(List<Trade> trades) {
        List<BooleanResultMessage> resultMessages = new ArrayList<>();

        for (ISharedTradeClearingRule sharedTradeClearingRule :
                sharedTradeClearingRules) {
            BooleanResultMessage evaluationResultMessage =
                    sharedTradeClearingRule.checkRule(trades);

            if (!evaluationResultMessage.getOperationResult()) {
                resultMessages.add(evaluationResultMessage);
            }
        }

        for (IOneSideInstitutionalTradeClearingRule oneSideInstitutionalTradeClearingRule :
                oneSideInstitutionalTradeClearingRules) {
            BooleanResultMessage evaluationResultMessage =
                    oneSideInstitutionalTradeClearingRule.checkRule(trades);

            if (!evaluationResultMessage.getOperationResult()) {
                resultMessages.add(evaluationResultMessage);
            }
        }

        return resultMessages;
    }

    @Override
    public List<BooleanResultMessage> EvaluateTwoSideRetailTrades(List<Trade> trades) {
        List<BooleanResultMessage> resultMessages = new ArrayList<>();

        for (ISharedTradeClearingRule sharedTradeClearingRule :
                sharedTradeClearingRules) {
            BooleanResultMessage evaluationResultMessage =
                    sharedTradeClearingRule.checkRule(trades);

            if (!evaluationResultMessage.getOperationResult()) {
                resultMessages.add(evaluationResultMessage);
            }
        }

        for (ITwoSideRetailTradeClearingRule twoSideRetailTradeClearingRule :
                twoSideRetailTradeClearingRules) {
            BooleanResultMessage evaluationResultMessage =
                    twoSideRetailTradeClearingRule.checkRule(trades);

            if (!evaluationResultMessage.getOperationResult()) {
                resultMessages.add(evaluationResultMessage);
            }
        }

        return resultMessages;
    }
}
