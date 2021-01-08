package emSeco.clearingCropUnit.core.modules.tradeValidationRules.sharedTradeValidationRules.implementations;

//#if SharedTradeRoutingInformationValidation
import emSeco.clearingCropUnit.core.entities.trade.Trade;
import emSeco.clearingCropUnit.core.modules.tradeValidationRules.sharedTradeValidationRules.interfaces.ISharedTradeValidationRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;


public class SharedTradeRoutingInformationValidation implements ISharedTradeValidationRule {
    @Override
    public BooleanResultMessage checkRule(Trade trade) {
        if (trade.getExchangeId() == null)
        {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The exchangeId field for trade is empty!"));
        }

        if (trade.getTradeId() == null)
        {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The exchangeId field for trade is empty!"));
        }

        if (trade.getBuySide().getRoutingInformation().getBrokerId() == null ||
                trade.getSellSide().getRoutingInformation().getBrokerId() == null)
        {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The brokerId field of one or two sides are empty!"));
        }

        return new BooleanResultMessage
                (true, OperationMessage.emptyOperationMessage());
    }
}
//#endif