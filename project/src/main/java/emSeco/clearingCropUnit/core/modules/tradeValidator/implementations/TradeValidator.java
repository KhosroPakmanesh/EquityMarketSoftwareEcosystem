package emSeco.clearingCropUnit.core.modules.tradeValidator.implementations;

import com.google.inject.Inject;
import emSeco.clearingCropUnit.core.entities.trade.InstitutionalTradeType;
import emSeco.clearingCropUnit.core.entities.trade.Trade;
import emSeco.clearingCropUnit.core.modules.tradeValidationRules.oneSideInstitutionalTradeValidationRules.interfaces.IOneSideInstitutionalTradeValidationRule;
import emSeco.clearingCropUnit.core.modules.tradeValidationRules.sharedTradeValidationRules.interfaces.ISharedTradeValidationRule;
import emSeco.clearingCropUnit.core.modules.tradeValidationRules.twoSideInstitutionalTradeValidationRules.interfaces.ITwoSideInstitutionalTradeValidationRule;
import emSeco.clearingCropUnit.core.modules.tradeValidationRules.twoSideRetailTradeValidationRules.interfaces.ITwoSideRetailTradeValidationRule;
import emSeco.clearingCropUnit.core.modules.tradeValidator.interfaces.ITradeValidator;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TradeValidator implements ITradeValidator {
    private final Set<ITwoSideInstitutionalTradeValidationRule> twoSideInstitutionalTradeValidationRules;
    private final Set<IOneSideInstitutionalTradeValidationRule> oneSideInstitutionalTradeValidationRules;
    private final Set<ITwoSideRetailTradeValidationRule> twoSideRetailTradeValidationRules;
    private final Set<ISharedTradeValidationRule> sharedTradeValidationRules;

    @Inject
    public TradeValidator(
            Set<ITwoSideInstitutionalTradeValidationRule> twoSideInstitutionalTradeValidationRules,
            Set<IOneSideInstitutionalTradeValidationRule> oneSideInstitutionalTradeValidationRules,
            Set<ITwoSideRetailTradeValidationRule> twoSideRetailTradeValidationRules,
            Set<ISharedTradeValidationRule> sharedTradeValidationRules) {
        this.twoSideInstitutionalTradeValidationRules = twoSideInstitutionalTradeValidationRules;
        this.oneSideInstitutionalTradeValidationRules = oneSideInstitutionalTradeValidationRules;
        this.twoSideRetailTradeValidationRules = twoSideRetailTradeValidationRules;
        this.sharedTradeValidationRules = sharedTradeValidationRules;
    }

    @Override
    public List<BooleanResultMessage> validateTwoSideRetailTrade(Trade trade) {
        List<BooleanResultMessage> resultMessages = new ArrayList<>();

        for (ISharedTradeValidationRule sharedTradeValidationRule :
                sharedTradeValidationRules) {
            BooleanResultMessage ValidationResultMessage =
                    sharedTradeValidationRule.checkRule(trade);

            if (!ValidationResultMessage.getOperationResult()) {
                resultMessages.add(ValidationResultMessage);
            }
        }
        for (ITwoSideRetailTradeValidationRule twoSideRetailTradeValidationRule :
                twoSideRetailTradeValidationRules) {
            BooleanResultMessage validationResultMessage =
                    twoSideRetailTradeValidationRule.checkRule(trade);

            if (!validationResultMessage.getOperationResult()) {
                resultMessages.add(validationResultMessage);
            }
        }

        return resultMessages;
    }

    @Override
    public List<BooleanResultMessage> validateInstitutionalTrade(Trade trade) {
        List<BooleanResultMessage> resultMessages = new ArrayList<>();

        if (trade.getInstitutionalTradeType() == InstitutionalTradeType.twoSideInstitutional) {

            for (ISharedTradeValidationRule sharedTradeValidationRule :
                    sharedTradeValidationRules) {
                BooleanResultMessage ValidationResultMessage =
                        sharedTradeValidationRule.checkRule(trade);

                if (!ValidationResultMessage.getOperationResult()) {
                    resultMessages.add(ValidationResultMessage);
                }
            }
            for (ITwoSideInstitutionalTradeValidationRule twoSideInstitutionalTradeValidationRule :
                    twoSideInstitutionalTradeValidationRules) {
                BooleanResultMessage validationResultMessage =
                        twoSideInstitutionalTradeValidationRule.checkRule(trade);

                if (!validationResultMessage.getOperationResult()) {
                    resultMessages.add(validationResultMessage);
                }
            }

        } else if (trade.getInstitutionalTradeType() == InstitutionalTradeType.oneSideInstitutional) {

            for (ISharedTradeValidationRule sharedTradeValidationRule :
                    sharedTradeValidationRules) {
                BooleanResultMessage ValidationResultMessage =
                        sharedTradeValidationRule.checkRule(trade);

                if (!ValidationResultMessage.getOperationResult()) {
                    resultMessages.add(ValidationResultMessage);
                }
            }
            for (IOneSideInstitutionalTradeValidationRule oneSideInstitutionalTradeValidationRule :
                    oneSideInstitutionalTradeValidationRules) {
                BooleanResultMessage validationResultMessage =
                        oneSideInstitutionalTradeValidationRule.checkRule(trade);

                if (!validationResultMessage.getOperationResult()) {
                    resultMessages.add(validationResultMessage);
                }
            }

        } else {
            resultMessages.add(new BooleanResultMessage(false,
                    OperationMessage.Create("The trade is not institutional!")));
        }

        return resultMessages;
    }
}
