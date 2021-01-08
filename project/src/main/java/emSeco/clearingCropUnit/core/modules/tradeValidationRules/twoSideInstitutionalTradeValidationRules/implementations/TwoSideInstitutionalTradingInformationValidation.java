package emSeco.clearingCropUnit.core.modules.tradeValidationRules.twoSideInstitutionalTradeValidationRules.implementations;

//#if TwoSideInstitutionalTradingInformationValidation
import emSeco.clearingCropUnit.core.entities.trade.InstitutionalTradeType;
import emSeco.clearingCropUnit.core.entities.trade.Trade;
import emSeco.clearingCropUnit.core.modules.tradeValidationRules.twoSideInstitutionalTradeValidationRules.interfaces.ITwoSideInstitutionalTradeValidationRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;

public class TwoSideInstitutionalTradingInformationValidation implements ITwoSideInstitutionalTradeValidationRule {
    @Override
    public BooleanResultMessage checkRule(Trade trade) {
        if (trade.getInstitutionalTradeType() != InstitutionalTradeType.twoSideInstitutional) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The trade type is not two side institutional!"));
        }

        if (!((trade.getBuySide().getTradingInformation().getRegisteredCode() != null &&
                trade.getSellSide().getTradingInformation().getRegisteredCode() != null))) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The registered code field of two sides should be filled!"));
        }

        if (trade.getTradeOwnerSide().getTradingInformation().getClientTradingCode() == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The client trade Code field of the owner side is empty!"));
        }

        if (trade.getBuySide().getTradingInformation().getInitiatorType() == null ||
                trade.getSellSide().getTradingInformation().getInitiatorType() == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The order initiator type field of one or two sides are empty!"));
        }

        return new BooleanResultMessage
                (true, OperationMessage.emptyOperationMessage());
    }
}
//#endif
