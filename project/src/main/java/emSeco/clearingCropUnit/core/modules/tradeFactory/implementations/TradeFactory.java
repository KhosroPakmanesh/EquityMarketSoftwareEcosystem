package emSeco.clearingCropUnit.core.modules.tradeFactory.implementations;

import com.google.inject.Inject;
import emSeco.clearingCropUnit.core.entities.trade.Trade;
import emSeco.clearingCropUnit.core.modules.tradeFactory.interfaces.ITradeFactory;
import emSeco.clearingCropUnit.core.modules.tradeFactory.models.ConstructTradeOutputClass;
import emSeco.clearingCropUnit.core.modules.tradeValidator.interfaces.ITradeValidator;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;

import java.util.List;

public class TradeFactory implements ITradeFactory {
    private final ITradeValidator tradeValidator;

    @Inject
    public TradeFactory(ITradeValidator tradeValidator) {
        this.tradeValidator = tradeValidator;
    }

    @Override
    public ConstructTradeOutputClass constructTwoSideRetailTrade(Trade trade) {
        List<BooleanResultMessage> tradeValidationResultMessages =
                tradeValidator.validateTwoSideRetailTrade(trade);

        if (tradeValidationResultMessages.size() > 0) {
            return new ConstructTradeOutputClass(tradeValidationResultMessages, null);
        }

        return new ConstructTradeOutputClass(tradeValidationResultMessages, trade);
    }

    public ConstructTradeOutputClass constructInstitutionalTrade(Trade trade) {
        List<BooleanResultMessage> tradeValidationResultMessages =
                tradeValidator.validateInstitutionalTrade(trade);

        if (tradeValidationResultMessages.size() > 0) {
            return new ConstructTradeOutputClass(tradeValidationResultMessages, null);
        }

        return new ConstructTradeOutputClass(tradeValidationResultMessages, trade);
    }
}
