package emSeco.clearingCropUnit.infrastructure.services.gateways.clearingCorpApiGateways.implementations.gateways;

import com.google.inject.Inject;
import emSeco.clearingCropUnit.core.entities.settlementResult.SettlementResult;
import emSeco.clearingCropUnit.core.services.domainServices.clearingCorpServiceRegistry.interfaces.IClearingCorpServiceRegistry;
import emSeco.clearingCropUnit.core.services.infrastructureServices.gateways.clearingCorpApiGateway.interfaces.gateways.IClearingCorpToExchangeUnitApiGateway;
import emSeco.exchangeUnit.core.modules.exchange.interfaces.IExchange;
import emSeco.shared.architecturalConstructs.BooleanResultMessages;
import emSeco.shared.exceptions.ServiceRegistryMalfunctionException;

import java.util.List;

import static emSeco.clearingCropUnit.core.helpers.ClearingCorpToCustodianEntitiesMapper.mapFromClearingCorpToExchangeSettlementResults;

public class ClearingCorpToExchangeUnitApiGateway implements IClearingCorpToExchangeUnitApiGateway {
    private final IClearingCorpServiceRegistry clearingCorpServiceRegistry;

    @Inject
    public ClearingCorpToExchangeUnitApiGateway(IClearingCorpServiceRegistry clearingCorpServiceRegistry) {
        this.clearingCorpServiceRegistry = clearingCorpServiceRegistry;
    }

    public BooleanResultMessages submitSettlementResults(List<SettlementResult> settlementResults) {
        SettlementResult settlementResult = settlementResults.stream().findFirst().orElse(null);
        if (settlementResult == null) {
            throw new ServiceRegistryMalfunctionException
                    ("Clearing Corporation's service registry malfunctions!");
        }

        IExchange exchange = clearingCorpServiceRegistry.getExchanges()
                .stream().filter(exc -> exc.getExchangeId() == settlementResult.getTrade()
                        .getExchangeId()).findAny().orElse(null);

        if (exchange == null) {
            throw new ServiceRegistryMalfunctionException
                    ("Clearing Corporation's service registry malfunctions!");
        }

        return exchange.submitSettlementResults_API(
                mapFromClearingCorpToExchangeSettlementResults(settlementResults));
    }
}
