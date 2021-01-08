package emSeco.brokerUnit.infrastructure.services.gateways.brokerUnitApiGateways.implementations;

import com.google.inject.Inject;
import emSeco.brokerUnit.core.services.infrastructureServices.gateways.brokerUnitApiGateways.interfaces.gateways.IBrokerToClearingBankUnitApiGateway;
import emSeco.brokerUnit.core.services.infrastructureServices.gateways.brokerUnitApiGateways.interfaces.gateways.IBrokerToDepositoryUnitApiGateway;
import emSeco.brokerUnit.core.services.infrastructureServices.gateways.brokerUnitApiGateways.interfaces.IBrokerUnitApiGateways;
import emSeco.brokerUnit.core.services.infrastructureServices.gateways.brokerUnitApiGateways.interfaces.gateways.IBrokerToCustodianUnitApiGateway;
import emSeco.brokerUnit.core.services.infrastructureServices.gateways.brokerUnitApiGateways.interfaces.gateways.IBrokerToExchangeUnitApiGateway;

public class BrokerUnitApiGateways implements IBrokerUnitApiGateways {
    private final IBrokerToExchangeUnitApiGateway brokerToExchangeUnitApiGateway;
    private final IBrokerToCustodianUnitApiGateway brokerToCustodianUnitApiGateway;
    private final IBrokerToClearingBankUnitApiGateway brokerToClearingBankUnitApiGateway;
    private final IBrokerToDepositoryUnitApiGateway brokerToDepositoryUnitApiGateway;

    @Inject
    public BrokerUnitApiGateways(
    IBrokerToExchangeUnitApiGateway brokerToExchangeUnitApiGateway,
    IBrokerToCustodianUnitApiGateway brokerToCustodianUnitApiGateway,
    IBrokerToClearingBankUnitApiGateway brokerToClearingBankUnitApiGateway,
    IBrokerToDepositoryUnitApiGateway brokerToDepositoryUnitApiGateway){
        this.brokerToExchangeUnitApiGateway = brokerToExchangeUnitApiGateway;
        this.brokerToCustodianUnitApiGateway = brokerToCustodianUnitApiGateway;
        this.brokerToClearingBankUnitApiGateway = brokerToClearingBankUnitApiGateway;
        this.brokerToDepositoryUnitApiGateway = brokerToDepositoryUnitApiGateway;
    }

    @Override
    public IBrokerToExchangeUnitApiGateway getBrokerToExchangeUnitApiGateway() {
        return brokerToExchangeUnitApiGateway;
    }

    @Override
    public IBrokerToCustodianUnitApiGateway getBrokerToCustodianUnitApiGateway() {
        return brokerToCustodianUnitApiGateway;
    }

    @Override
    public IBrokerToClearingBankUnitApiGateway getBrokerToClearingBankUnitApiGateway() {
        return brokerToClearingBankUnitApiGateway;
    }

    @Override
    public IBrokerToDepositoryUnitApiGateway getBrokerToDepositoryUnitApiGateway() {
        return brokerToDepositoryUnitApiGateway;
    }
}
