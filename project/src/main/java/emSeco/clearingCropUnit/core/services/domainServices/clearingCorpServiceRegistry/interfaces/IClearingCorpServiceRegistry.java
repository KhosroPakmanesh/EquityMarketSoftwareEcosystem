package emSeco.clearingCropUnit.core.services.domainServices.clearingCorpServiceRegistry.interfaces;

import emSeco.clearingbankUnit.core.modules.clearingBank.interfaces.IClearingBank;
import emSeco.custodianUnit.core.modules.custodian.interfaces.ICustodian;
import emSeco.depositoryUnit.core.modules.depository.interfaces.IDepository;
import emSeco.exchangeUnit.core.modules.exchange.interfaces.IExchange;

import java.util.List;

public interface IClearingCorpServiceRegistry {
    void initializeRegistry(
            List<ICustodian> custodians,
            List<IExchange> exchanges,
            List<IClearingBank> clearingBanks,
            IDepository depository
    );

    List<ICustodian> getCustodians();

    List<IExchange> getExchanges();

    List<IClearingBank> getClearingBanks();

    IDepository getDepository();
}
