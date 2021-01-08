package emSeco.clearingCropUnit.core.modules.clearingCorp.interfaces;

import emSeco.clearingCropUnit.core.entities.shared.AccountsInformation;
import emSeco.clearingCropUnit.core.entities.trade.Trade;
import emSeco.clearingCropUnit.core.modules.tradeSettler.models.SettleTradesOutputClass;
import emSeco.shared.architecturalConstructs.BooleanResultMessages;

import java.util.List;

public interface IClearingCorp {
    void setClearingCorpInfo(AccountsInformation accountsInformation);

    List<BooleanResultMessages> submitRetailTrades_API(List<Trade> trades);

    List<BooleanResultMessages> submitInstitutionalTrades_API(List<Trade> trades);

    SettleTradesOutputClass settleTrades_REC();
}
