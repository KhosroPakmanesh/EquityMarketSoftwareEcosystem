package emSeco.exchangeUnit.core.services.domainServices.exchangeServiceRegistry.interfaces;

import emSeco.brokerUnit.core.modules.broker.interfaces.IBroker;
import emSeco.clearingCropUnit.core.modules.clearingCorp.interfaces.IClearingCorp;

import java.util.List;

public interface IExchangeServiceRegistry {
    void initializeRegistry(
            List<IBroker> brokers,
            IClearingCorp clearingCorp
    );

    List<IBroker> getBrokers();

    IClearingCorp getClearingCorp();
}
