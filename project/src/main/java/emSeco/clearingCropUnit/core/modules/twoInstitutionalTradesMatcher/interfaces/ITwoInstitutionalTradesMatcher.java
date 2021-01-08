package emSeco.clearingCropUnit.core.modules.twoInstitutionalTradesMatcher.interfaces;

import emSeco.clearingCropUnit.core.entities.trade.Trade;

import java.util.List;

public interface ITwoInstitutionalTradesMatcher {
    List<Trade> MatchTwoTradeSides(List<Trade> trades);
}
