package emSeco.exchangeUnit.core.services.domainServices.exchangeServiceRegistry.implementations;

import emSeco.brokerUnit.core.modules.broker.interfaces.IBroker;
import emSeco.clearingCropUnit.core.modules.clearingCorp.interfaces.IClearingCorp;
import emSeco.exchangeUnit.core.services.domainServices.exchangeServiceRegistry.interfaces.IExchangeServiceRegistry;

import java.util.List;

public class ObjectReferenceExchangeServiceRegistry implements IExchangeServiceRegistry {

    private List<IBroker> brokers;
    private IClearingCorp clearingCorp;

    public void initializeRegistry(
            List<IBroker> brokers,
            IClearingCorp clearingCorp
    ) {
        this.brokers = brokers;
        this.clearingCorp = clearingCorp;
    }

    @Override
    public List<IBroker> getBrokers() {
        return brokers;
    }

    public IClearingCorp getClearingCorp() {
        return clearingCorp;
    }
}
