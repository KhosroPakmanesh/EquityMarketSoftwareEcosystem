package emSeco.custodianUnit.core.services.infrastructureServices.gateways.custodianUnitApiGateway.interfaces.gateways;


public interface ICustodianUnitApiGateways {

    ICustodianToBrokerUnitApiGateway getCustodianToBrokerUnitApiGateway();
    ICustodianToClearingCorpUnitApiGateway getCustodianToClearingCorpUnitApiGateway();
    ICustodianToClearingBankUnitApiGateway getCustodianToClearingBankUnitApiGateway();
    ICustodianToDepositoryUnitApiGateway getCustodianToDepositoryUnitApiGateway();
}
