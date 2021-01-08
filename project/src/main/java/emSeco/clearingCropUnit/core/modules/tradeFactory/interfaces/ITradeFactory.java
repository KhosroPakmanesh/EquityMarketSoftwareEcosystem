package emSeco.clearingCropUnit.core.modules.tradeFactory.interfaces;

import emSeco.clearingCropUnit.core.entities.trade.Trade;
import emSeco.clearingCropUnit.core.modules.tradeFactory.models.ConstructTradeOutputClass;

public interface ITradeFactory {
    ConstructTradeOutputClass constructTwoSideRetailTrade(Trade trade);

    ConstructTradeOutputClass constructInstitutionalTrade(Trade trade);
}
