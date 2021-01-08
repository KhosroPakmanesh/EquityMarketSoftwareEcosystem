package emSeco.clearingCropUnit.core.services.infrastructureServices.gateways.clearingCorpApiGateway.interfaces.gateways;

import emSeco.clearingCropUnit.core.entities.settlementResult.SettlementResult;
import emSeco.shared.architecturalConstructs.BooleanResultMessages;

import java.util.List;

public interface IClearingCorpToExchangeUnitApiGateway {
    BooleanResultMessages submitSettlementResults(List<SettlementResult> settlementResults);
}
