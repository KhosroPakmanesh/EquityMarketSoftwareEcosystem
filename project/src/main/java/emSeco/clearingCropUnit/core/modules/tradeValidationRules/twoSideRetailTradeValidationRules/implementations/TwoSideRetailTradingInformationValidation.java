package emSeco.clearingCropUnit.core.modules.tradeValidationRules.twoSideRetailTradeValidationRules.implementations;

//#if TwoSideRetailTradingInformationValidation
import emSeco.clearingCropUnit.core.entities.trade.InstitutionalTradeType;
import emSeco.clearingCropUnit.core.entities.trade.Trade;
import emSeco.clearingCropUnit.core.modules.tradeValidationRules.twoSideRetailTradeValidationRules.interfaces.ITwoSideRetailTradeValidationRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;

public class TwoSideRetailTradingInformationValidation implements ITwoSideRetailTradeValidationRule {

    @Override
    public BooleanResultMessage checkRule(Trade trade) {
        if (trade.getInstitutionalTradeType() != InstitutionalTradeType.TwoSideRetail)
        {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The trade type is not two side retail!"));
        }

        if (trade.getBuySide().getTradingInformation().getClientTradingCode() == null ||
                trade.getSellSide().getTradingInformation().getClientTradingCode() == null)
        {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The client trade Code field of one or two sides are empty!"));
        }

        if (trade.getBuySide().getTradingInformation().getInitiatorType() == null ||
                trade.getSellSide().getTradingInformation().getInitiatorType() == null)
        {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The order initiator type field of one or two sides are empty!"));
        }

        return new BooleanResultMessage
                (true, OperationMessage.emptyOperationMessage());
    }
}
//#endif
