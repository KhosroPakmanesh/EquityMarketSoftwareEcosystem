package emSeco.clearingCropUnit.core.modules.tradeSettler.interfaces;

import emSeco.clearingCropUnit.core.entities.trade.Trade;
import emSeco.clearingCropUnit.core.modules.tradeSettler.models.SettleTradesOutputClass;

import java.util.List;

public interface ITradeSettler {
    SettleTradesOutputClass settleTrades(List<Trade> trades);
}
