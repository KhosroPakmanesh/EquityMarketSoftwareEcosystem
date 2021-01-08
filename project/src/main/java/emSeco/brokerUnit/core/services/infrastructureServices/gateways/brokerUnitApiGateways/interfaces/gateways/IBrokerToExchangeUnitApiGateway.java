package emSeco.brokerUnit.core.services.infrastructureServices.gateways.brokerUnitApiGateways.interfaces.gateways;

import emSeco.brokerUnit.core.entities.order.InstitutionalOrder;
import emSeco.brokerUnit.core.entities.order.RetailOrder;
import emSeco.shared.architecturalConstructs.BooleanResultMessages;

public interface IBrokerToExchangeUnitApiGateway {
    BooleanResultMessages submitRetailOrder(RetailOrder retailOrder);
    BooleanResultMessages submitInstitutionalOrder(InstitutionalOrder institutionalOrder);
}
