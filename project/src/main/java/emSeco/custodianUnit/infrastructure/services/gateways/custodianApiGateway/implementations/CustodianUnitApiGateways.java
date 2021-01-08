package emSeco.custodianUnit.infrastructure.services.gateways.custodianApiGateway.implementations;

import com.google.inject.Inject;
import emSeco.custodianUnit.core.services.infrastructureServices.gateways.custodianUnitApiGateway.interfaces.gateways.*;

public class CustodianUnitApiGateways implements ICustodianUnitApiGateways {

    private final ICustodianToBrokerUnitApiGateway custodianToBrokerUnitApiGateway;
    private final ICustodianToClearingCorpUnitApiGateway custodianToClearingCorpUnitApiGateway;
    private final ICustodianToClearingBankUnitApiGateway custodianToClearingBankUnitApiGateway;
    private final ICustodianToDepositoryUnitApiGateway custodianToDepositoryUnitApiGateway;

    @Inject
    public CustodianUnitApiGateways(ICustodianToBrokerUnitApiGateway custodianToBrokerUnitApiGateway,
                                    ICustodianToClearingCorpUnitApiGateway custodianToClearingCorpUnitApiGateway,
                                    ICustodianToClearingBankUnitApiGateway custodianToClearingBankUnitApiGateway,
                                    ICustodianToDepositoryUnitApiGateway custodianToDepositoryUnitApiGateway) {
        this.custodianToBrokerUnitApiGateway = custodianToBrokerUnitApiGateway;
        this.custodianToClearingCorpUnitApiGateway = custodianToClearingCorpUnitApiGateway;
        this.custodianToClearingBankUnitApiGateway = custodianToClearingBankUnitApiGateway;
        this.custodianToDepositoryUnitApiGateway = custodianToDepositoryUnitApiGateway;
    }


    @Override
    public ICustodianToBrokerUnitApiGateway getCustodianToBrokerUnitApiGateway() {
        return custodianToBrokerUnitApiGateway;
    }

    @Override
    public ICustodianToClearingCorpUnitApiGateway getCustodianToClearingCorpUnitApiGateway() {
        return custodianToClearingCorpUnitApiGateway;
    }

    @Override
    public ICustodianToClearingBankUnitApiGateway getCustodianToClearingBankUnitApiGateway() {
        return custodianToClearingBankUnitApiGateway;
    }

    @Override
    public ICustodianToDepositoryUnitApiGateway getCustodianToDepositoryUnitApiGateway() {
        return custodianToDepositoryUnitApiGateway;
    }
}