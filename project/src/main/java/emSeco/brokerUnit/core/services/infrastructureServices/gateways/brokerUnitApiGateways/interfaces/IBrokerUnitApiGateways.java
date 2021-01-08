package emSeco.brokerUnit.core.services.infrastructureServices.gateways.brokerUnitApiGateways.interfaces;

import emSeco.brokerUnit.core.services.infrastructureServices.gateways.brokerUnitApiGateways.interfaces.gateways.IBrokerToClearingBankUnitApiGateway;
import emSeco.brokerUnit.core.services.infrastructureServices.gateways.brokerUnitApiGateways.interfaces.gateways.IBrokerToCustodianUnitApiGateway;
import emSeco.brokerUnit.core.services.infrastructureServices.gateways.brokerUnitApiGateways.interfaces.gateways.IBrokerToDepositoryUnitApiGateway;
import emSeco.brokerUnit.core.services.infrastructureServices.gateways.brokerUnitApiGateways.interfaces.gateways.IBrokerToExchangeUnitApiGateway;

public interface IBrokerUnitApiGateways {
    IBrokerToExchangeUnitApiGateway getBrokerToExchangeUnitApiGateway() ;
    IBrokerToCustodianUnitApiGateway getBrokerToCustodianUnitApiGateway();
    IBrokerToClearingBankUnitApiGateway getBrokerToClearingBankUnitApiGateway();
    IBrokerToDepositoryUnitApiGateway getBrokerToDepositoryUnitApiGateway();
}