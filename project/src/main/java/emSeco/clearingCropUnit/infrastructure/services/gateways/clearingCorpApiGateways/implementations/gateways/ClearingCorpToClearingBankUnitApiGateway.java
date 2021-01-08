package emSeco.clearingCropUnit.infrastructure.services.gateways.clearingCorpApiGateways.implementations.gateways;

import com.google.inject.Inject;
import emSeco.clearingCropUnit.core.services.domainServices.clearingCorpServiceRegistry.interfaces.IClearingCorpServiceRegistry;
import emSeco.clearingCropUnit.core.services.infrastructureServices.gateways.clearingCorpApiGateway.interfaces.gateways.IClearingCorpToClearingBankUnitApiGateway;
import emSeco.clearingbankUnit.core.modules.clearingBank.interfaces.IClearingBank;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.exceptions.ServiceRegistryMalfunctionException;

import java.util.UUID;

public class ClearingCorpToClearingBankUnitApiGateway implements IClearingCorpToClearingBankUnitApiGateway {
    private final IClearingCorpServiceRegistry clearingCorpServiceRegistry;

    @Inject
    public ClearingCorpToClearingBankUnitApiGateway(IClearingCorpServiceRegistry clearingCorpServiceRegistry) {
        this.clearingCorpServiceRegistry = clearingCorpServiceRegistry;
    }

    @Override
    public BooleanResultMessage debit
            (UUID clearingBankId, UUID clearingBankAccountNumber, double totalAmount) {

        IClearingBank foundClearingBank = getClearingBank(clearingBankId);
        if (foundClearingBank == null) {
            throw new ServiceRegistryMalfunctionException
                    ("Clearing Corporation's service registry malfunctions!");
        }

        return foundClearingBank.debit_API(clearingBankAccountNumber, totalAmount);
    }

    @Override
    public BooleanResultMessage credit
            (UUID clearingBankId, UUID clearingBankAccountNumber, double totalAmount) {

        IClearingBank foundClearingBank = getClearingBank(clearingBankId);
        if (foundClearingBank == null) {
            throw new ServiceRegistryMalfunctionException
                    ("Clearing Corporation's service registry malfunctions!");
        }

        return foundClearingBank.credit_API(clearingBankAccountNumber, totalAmount);
    }

    @Override
    public BooleanResultMessage checkBalance
            (UUID clearingBankId, UUID clearingBankAccountNumber, double totalAmount) {

        IClearingBank foundClearingBank = getClearingBank(clearingBankId);
        if (foundClearingBank == null) {
            throw new ServiceRegistryMalfunctionException
                    ("Clearing Corporation's service registry malfunctions!");
        }

        return foundClearingBank.checkBalance_API(clearingBankAccountNumber, totalAmount);
    }

    private IClearingBank getClearingBank(UUID clearingBankId) {
        return clearingCorpServiceRegistry.getClearingBanks().stream().
                filter(clearingBank -> clearingBank.getClearingBankId() == clearingBankId).
                findAny().orElse(null);
    }
}
