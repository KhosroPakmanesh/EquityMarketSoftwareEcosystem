package emSeco.custodianUnit.core.services.domainServices.custodianServiceRegistry.implementations;

import emSeco.brokerUnit.core.modules.broker.interfaces.IBroker;
import emSeco.clearingCropUnit.core.modules.clearingCorp.interfaces.IClearingCorp;
import emSeco.clearingbankUnit.core.modules.clearingBank.interfaces.IClearingBank;
import emSeco.custodianUnit.core.services.domainServices.custodianServiceRegistry.interfaces.ICustodianServiceRegistry;
import emSeco.depositoryUnit.core.modules.depository.interfaces.IDepository;

import java.util.ArrayList;
import java.util.List;

public class ObjectReferenceCustodianServiceRegistry implements ICustodianServiceRegistry {
    private List<IBroker> brokers;
    private IClearingCorp clearingCorp;
    private List<IClearingBank> clearingBanks;
    private IDepository depository;


    @Override
    public void initializeRegistry(
            ArrayList<IBroker> brokers,
            IClearingCorp clearingCorp,
            ArrayList<IClearingBank> clearingBanks,
            IDepository depository) {
        this.brokers = brokers;
        this.clearingCorp = clearingCorp;
        this.clearingBanks = clearingBanks;
        this.depository = depository;
    }

    @Override
    public List<IBroker> getBrokers() {
        return brokers;
    }

    @Override
    public IClearingCorp getClearingCorp() {
        return clearingCorp;
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
