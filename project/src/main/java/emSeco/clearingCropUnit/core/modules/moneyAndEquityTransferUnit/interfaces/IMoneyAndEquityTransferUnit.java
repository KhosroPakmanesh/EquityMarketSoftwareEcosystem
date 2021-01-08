package emSeco.clearingCropUnit.core.modules.moneyAndEquityTransferUnit.interfaces;

import emSeco.clearingCropUnit.core.entities.trade.Trade;
import emSeco.clearingCropUnit.core.modules.moneyAndEquityTransferUnit.models.NovateMoneyAndSharesOutputClass;

import java.util.List;

public interface IMoneyAndEquityTransferUnit {
    NovateMoneyAndSharesOutputClass NovateMoneyAndShares(List<Trade> trades);
}
