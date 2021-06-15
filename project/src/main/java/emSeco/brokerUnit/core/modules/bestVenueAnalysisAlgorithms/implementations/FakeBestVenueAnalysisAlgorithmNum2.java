package emSeco.brokerUnit.core.modules.bestVenueAnalysisAlgorithms.implementations;

//#if FakeBestVenueAnalysisAlgorithmNum2
import com.google.inject.Inject;
import emSeco.brokerUnit.core.modules.bestVenueAnalysisAlgorithms.interfaces.IBestVenueAnalysisAlgorithm;
import emSeco.brokerUnit.core.services.domainServices.brokerServiceRegistry.interfaces.IBrokerServiceRegistry;
import emSeco.exchangeUnit.core.modules.exchange.interfaces.IExchange;
import emSeco.shared.exceptions.ServiceRegistryMalfunctionException;

import java.util.UUID;

public class FakeBestVenueAnalysisAlgorithmNum2 implements IBestVenueAnalysisAlgorithm {
    private final IBrokerServiceRegistry brokerServiceRegistry;

    @Inject
    public FakeBestVenueAnalysisAlgorithmNum2(IBrokerServiceRegistry brokerServiceRegistry) {
        this.brokerServiceRegistry = brokerServiceRegistry;
    }

    @Override
    public UUID ChooseBestVenue() {
        IExchange exchange = brokerServiceRegistry.getExchanges().stream().findFirst().orElse(null);

        if (exchange == null) {
            throw new ServiceRegistryMalfunctionException
                    ("Broker's service registry malfunctions!");
        }

        return exchange.getExchangeId();
    }
}
//#endif
