package emSeco.clearingCropUnit.core.services.domainServices.clearingCorpServiceRegistry.implementations;

import emSeco.clearingCropUnit.core.services.domainServices.clearingCorpServiceRegistry.interfaces.IClearingCorpServiceRegistry;
import emSeco.clearingbankUnit.core.modules.clearingBank.interfaces.IClearingBank;
import emSeco.custodianUnit.core.modules.custodian.interfaces.ICustodian;
import emSeco.depositoryUnit.core.modules.depository.interfaces.IDepository;
import emSeco.exchangeUnit.core.modules.exchange.interfaces.IExchange;

import java.util.List;

public class ObjectReferenceClearingCorpServiceRegistry implements IClearingCorpServiceRegistry {

    private List<ICustodian> custodians;
    private List<IExchange> exchanges;
    private List<IClearingBank> clearingBanks;
    private IDepository depository;

    public void initializeRegistry(
            List<ICustodian> custodians,
            List<IExchange> exchanges,
            List<IClearingBank> clearingBanks,
            IDepository depository
    ) {
        this.custodians = custodians;
        this.exchanges = exchanges;
        this.clearingBanks = clearingBanks;
        this.depository = depository;
    }

    public List<ICustodian> getCustodians() {
        return custodians;
    }

    public List<IExchange> getExchanges() {
        return exchanges;
    }

    public List<IClearingBank> getClearingBanks() {
        return clearingBanks;
    }

    public IDepository getDepository() {
        return depository;
    }
}
