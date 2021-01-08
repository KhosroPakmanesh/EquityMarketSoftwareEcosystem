package emSeco.custodianUnit.infrastructure.services.databases.custodianRepositories.implementations.repositories;

import emSeco.custodianUnit.core.entities.custodianBankAccount.CustodianBankAccount;
import emSeco.custodianUnit.core.services.infrastructureServices.databases.custodianUnitRepositories.interfaces.repositories.ICustodianBankAccountRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustodianBankAccountRepository implements ICustodianBankAccountRepository {
    private final List<CustodianBankAccount> custodianBankAccounts;

    public CustodianBankAccountRepository() {
        this.custodianBankAccounts = new ArrayList<>();
    }

    @Override
    public void add(CustodianBankAccount custodianBankAccount) {
        this.custodianBankAccounts.add(custodianBankAccount);
    }

    @Override
    public CustodianBankAccount get(UUID custodianBankAccountNumber) {
        return custodianBankAccounts.stream().
                filter(custodianBankAccount -> custodianBankAccount.getCustodianBankAccountNumber()
                        == custodianBankAccountNumber).findAny().orElse(null);
    }
}
