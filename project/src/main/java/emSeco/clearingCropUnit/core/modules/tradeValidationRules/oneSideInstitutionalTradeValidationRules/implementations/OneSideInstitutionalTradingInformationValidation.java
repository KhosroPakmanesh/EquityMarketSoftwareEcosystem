package emSeco.clearingCropUnit.core.modules.tradeValidationRules.oneSideInstitutionalTradeValidationRules.implementations;

//#if OneSideInstitutionalTradingInformationValidation
import emSeco.clearingCropUnit.core.entities.trade.InstitutionalTradeType;
import emSeco.clearingCropUnit.core.entities.trade.Trade;
import emSeco.clearingCropUnit.core.modules.tradeValidationRules.oneSideInstitutionalTradeValidationRules.interfaces.IOneSideInstitutionalTradeValidationRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;


public class OneSideInstitutionalTradingInformationValidation implements IOneSideInstitutionalTradeValidationRule {
    @Override
    public BooleanResultMessage checkRule(Trade trade) {
        if (trade.getInstitutionalTradeType() != InstitutionalTradeType.oneSideInstitutional) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The trade type is not one side institutional!"));
        }

        if (trade.getTradeOwnerSide().getTradingInformation().getRegisteredCode() == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The registered code field of the owner side should be filled!"));
        }

        if (trade.getBuySide().getTradingInformation().getClientTradingCode() == null ||
                trade.getSellSide().getTradingInformation().getClientTradingCode() == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The client trade Code field of one or two sides are empty!"));
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
