package emSeco.custodianUnit.infrastructure.services.gateways.custodianApiGateway.implementations.gateways;

import com.google.inject.Inject;
import emSeco.clearingbankUnit.core.modules.clearingBank.interfaces.IClearingBank;
import emSeco.custodianUnit.core.services.domainServices.custodianServiceRegistry.interfaces.ICustodianServiceRegistry;
import emSeco.custodianUnit.core.services.infrastructureServices.gateways.custodianUnitApiGateway.interfaces.gateways.ICustodianToClearingBankUnitApiGateway;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;

import emSeco.shared.exceptions.ServiceRegistryMalfunctionException;

import java.util.UUID;

public class CustodianToClearingBankUnitApiGateway implements ICustodianToClearingBankUnitApiGateway {
    private final ICustodianServiceRegistry custodianServiceRegistry;

    @Inject
    public CustodianToClearingBankUnitApiGateway(ICustodianServiceRegistry custodianServiceRegistry) {
        this.custodianServiceRegistry = custodianServiceRegistry;
    }

    @Override
    public BooleanResultMessage debit(UUID clearingBankId, UUID clearingBankAccountNumber, double totalAmount) {
        IClearingBank foundClearingBank = getClearingBank(clearingBankId);

        if (foundClearingBank == null)
        {
            throw new ServiceRegistryMalfunctionException
                    ("Custodian's service registry malfunctions!");
        }

        return foundClearingBank.debit_API(clearingBankAccountNumber,totalAmount);
    }

    @Override
    public BooleanResultMessage credit(UUID clearingBankId, UUID clearingBankAccountNumber, double totalAmount) {
        IClearingBank foundClearingBank = getClearingBank(clearingBankId);
        if (foundClearingBank == null)
        {
            throw new ServiceRegistryMalfunctionException
                    ("Custodian's service registry malfunctions!");
        }

        return foundClearingBank.credit_API(clearingBankAccountNumber,totalAmount);
    }

    @Override
    public BooleanResultMessage checkBalance
            (UUID clearingBankId, UUID clearingBankAccountNumber,double totalAmount) {

        IClearingBank foundClearingBank = getClearingBank(clearingBankId);
        if (foundClearingBank == null)
        {
            throw new ServiceRegistryMalfunctionException
                    ("Custodian's service registry malfunctions!");
        }

        return foundClearingBank.checkBalance_API(clearingBankAccountNumber,totalAmount);
    }

    private IClearingBank getClearingBank(UUID clearingBankId) {
        return custodianServiceRegistry.getClearingBanks().stream().
                filter(clearingBank -> clearingBank.getClearingBankId() == clearingBankId).
                findAny().orElse(null);
    }
}
