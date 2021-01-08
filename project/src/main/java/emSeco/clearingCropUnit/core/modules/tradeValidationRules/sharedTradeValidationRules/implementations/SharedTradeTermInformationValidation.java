package emSeco.clearingCropUnit.core.modules.tradeValidationRules.sharedTradeValidationRules.implementations;

//#if SharedTradeTermInformationValidation
import emSeco.clearingCropUnit.core.entities.trade.Trade;
import emSeco.clearingCropUnit.core.modules.tradeValidationRules.sharedTradeValidationRules.interfaces.ISharedTradeValidationRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;


public class SharedTradeTermInformationValidation implements ISharedTradeValidationRule {
    @Override
    public BooleanResultMessage checkRule(Trade trade) {
        if (trade.getBuySide().getTerm().getInstrumentName() == null ||
                trade.getSellSide().getTerm().getInstrumentName() == null)
        {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The instrument name field of one or two sides are empty!"));
        }
        if (trade.getBuySide().getTerm().getPrice() == 0 ||
                trade.getSellSide().getTerm().getPrice() == 0)
        {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The price field of one or two sides are empty!"));
        }

        if (trade.getBuySide().getTerm().getQuantity() == 0 ||
                trade.getSellSide().getTerm().getQuantity() == 0)
        {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The quantity field of one or two sides are empty!"));
        }

        return new BooleanResultMessage
                (true, OperationMessage.emptyOperationMessage());
    }
}
//#endif
