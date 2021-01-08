package emSeco.brokerUnit.core.services.domainServices.brokerServiceRegistry.implementations;

import emSeco.brokerUnit.core.services.domainServices.brokerServiceRegistry.interfaces.IBrokerServiceRegistry;
import emSeco.clearingbankUnit.core.modules.clearingBank.interfaces.IClearingBank;
import emSeco.custodianUnit.core.modules.custodian.interfaces.ICustodian;
import emSeco.depositoryUnit.core.modules.depository.interfaces.IDepository;
import emSeco.exchangeUnit.core.modules.exchange.interfaces.IExchange;

import java.util.List;

public class ObjectReferenceBrokerServiceRegistry implements IBrokerServiceRegistry {
    private List<ICustodian> custodians;
    private List<IExchange> exchanges;
    private List<IClearingBank> clearingBanks;
    private IDepository depository;

    public void initializeRegistry(
            List<ICustodian> custodians,
            List<IExchange> exchanges,
            List<IClearingBank> clearingBanks,
            IDepository depository
    )
    {
        this.custodians = custodians;
        this.exchanges = exchanges;
        this.clearingBanks = clearingBanks;
        this.depository = depository;
    }

    @Override
    public List<ICustodian> getCustodians() {
        return custodians;
    }

    @Override
    public List<IExchange> getExchanges() {
        return exchanges;
    }

    @Override
    public List<IClearingBank> getClearingBanks() {
        return clearingBanks;
    }

    @Override
    public IDepository getDepository() {
        return depository;
    }
}
