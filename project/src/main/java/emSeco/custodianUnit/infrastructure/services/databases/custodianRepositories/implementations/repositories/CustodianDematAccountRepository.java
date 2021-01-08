package emSeco.custodianUnit.infrastructure.services.databases.custodianRepositories.implementations.repositories;

import emSeco.custodianUnit.core.entities.custodianDematAccount.CustodianDematAccount;
import emSeco.custodianUnit.core.services.infrastructureServices.databases.custodianUnitRepositories.interfaces.repositories.ICustodianDematAccountRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustodianDematAccountRepository implements ICustodianDematAccountRepository {
    private final List<CustodianDematAccount> custodianDematAccounts;

    public CustodianDematAccountRepository() {
        this.custodianDematAccounts = new ArrayList<>();
    }

    @Override
    public void add(CustodianDematAccount custodianDematAccount) {
        custodianDematAccounts.add(custodianDematAccount);
    }

    @Override
    public CustodianDematAccount get(UUID custodianDematAccountNumber) {
        return custodianDematAccounts.stream().
                filter(custodianDematAccount -> custodianDematAccount.getCustodianBankAccountNumber() ==
                        custodianDematAccountNumber).findAny().orElse(null);
    }
}
