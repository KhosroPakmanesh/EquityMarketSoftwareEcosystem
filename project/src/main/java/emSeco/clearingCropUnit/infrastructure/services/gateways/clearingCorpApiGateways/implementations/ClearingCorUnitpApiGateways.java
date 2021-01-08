package emSeco.clearingCropUnit.infrastructure.services.gateways.clearingCorpApiGateways.implementations;

import com.google.inject.Inject;
import emSeco.clearingCropUnit.core.services.infrastructureServices.gateways.clearingCorpApiGateway.interfaces.*;
import emSeco.clearingCropUnit.core.services.infrastructureServices.gateways.clearingCorpApiGateway.interfaces.gateways.IClearingCorpToClearingBankUnitApiGateway;
import emSeco.clearingCropUnit.core.services.infrastructureServices.gateways.clearingCorpApiGateway.interfaces.gateways.IClearingCorpToCustodianUnitApiGateway;
import emSeco.clearingCropUnit.core.services.infrastructureServices.gateways.clearingCorpApiGateway.interfaces.gateways.IClearingCorpToDepositoryUnitApiGateway;
import emSeco.clearingCropUnit.core.services.infrastructureServices.gateways.clearingCorpApiGateway.interfaces.gateways.IClearingCorpToExchangeUnitApiGateway;

public class ClearingCorUnitpApiGateways implements IClearingCorUnitpApiGateways {
    private final IClearingCorpToExchangeUnitApiGateway clearingCorpToExchangeUnitApiGateway;
    private final IClearingCorpToClearingBankUnitApiGateway clearingCorpToClearingBankUnitApiGateway;
    private final IClearingCorpToDepositoryUnitApiGateway clearingCorpToDepositoryUnitApiGateway;
    private final IClearingCorpToCustodianUnitApiGateway clearingCorpToCustodianUnitApiGateway;

    @Inject
    public ClearingCorUnitpApiGateways(IClearingCorpToExchangeUnitApiGateway clearingCorpToExchangeUnitApiGateway,
                                       IClearingCorpToClearingBankUnitApiGateway clearingCorpToClearingBankUnitApiGateway,
                                       IClearingCorpToDepositoryUnitApiGateway clearingCorpToDepositoryUnitApiGateway,
                                       IClearingCorpToCustodianUnitApiGateway clearingCorpToCustodianUnitApiGateway) {
        this.clearingCorpToExchangeUnitApiGateway = clearingCorpToExchangeUnitApiGateway;
        this.clearingCorpToClearingBankUnitApiGateway = clearingCorpToClearingBankUnitApiGateway;
        this.clearingCorpToDepositoryUnitApiGateway = clearingCorpToDepositoryUnitApiGateway;
        this.clearingCorpToCustodianUnitApiGateway = clearingCorpToCustodianUnitApiGateway;
    }

    @Override
    public IClearingCorpToClearingBankUnitApiGateway getClearingCorpToClearingBankUnitApiGateway() {
        return clearingCorpToClearingBankUnitApiGateway;
    }
    @Override
    public IClearingCorpToDepositoryUnitApiGateway getClearingCorpToDepositoryUnitApiGateway() {
        return clearingCorpToDepositoryUnitApiGateway;
    }

    @Override
    public IClearingCorpToCustodianUnitApiGateway getClearingCorpToCustodianUnitApiGateway() {
        return clearingCorpToCustodianUnitApiGateway;
    }

    @Override
    public IClearingCorpToExchangeUnitApiGateway getClearingCorpToExchangeUnitApiGateway() {
        return clearingCorpToExchangeUnitApiGateway;
    }
}
