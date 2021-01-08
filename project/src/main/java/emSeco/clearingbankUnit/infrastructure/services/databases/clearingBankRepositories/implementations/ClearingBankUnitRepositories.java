package emSeco.clearingbankUnit.infrastructure.services.databases.clearingBankRepositories.implementations;

import com.google.inject.Inject;
import emSeco.clearingbankUnit.core.services.infrastructureServices.databases.clearingBankRepositories.interfaces.IClearingBankUnitRepositories;
import emSeco.clearingbankUnit.core.services.infrastructureServices.databases.clearingBankRepositories.interfaces.repositories.IBankAccountRepository;

public class ClearingBankUnitRepositories implements IClearingBankUnitRepositories {

    private final IBankAccountRepository bankAccountRepository;

    @Inject
    public ClearingBankUnitRepositories(IBankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public IBankAccountRepository getBankAccountRepository() {
        return bankAccountRepository;
    }
}
