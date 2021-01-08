package emSeco.custodianUnit.core.services.infrastructureServices.gateways.custodianUnitApiGateway.interfaces.gateways;

import emSeco.custodianUnit.core.entities.trade.Trade;
import emSeco.shared.architecturalConstructs.BooleanResultMessages;

import java.util.List;

public interface ICustodianToClearingCorpUnitApiGateway {
    List<BooleanResultMessages> submitInstitutionalTrades(List<Trade> trades);
}
