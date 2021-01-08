package emSeco.depositoryUnit.infrastructure.services.databases.depositoryRepositories.implementations.repositories;

import emSeco.depositoryUnit.core.entities.dematAccount.DematAccount;
import emSeco.depositoryUnit.core.services.infrastructureServices.databases.depositoryRepositories.interfaces.repositories.IDematAccountRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DematAccountRepository implements IDematAccountRepository {
    private final List<DematAccount> dematAccounts;

    public DematAccountRepository() {
        this.dematAccounts = new ArrayList<>();
    }

    @Override
    public DematAccount get(UUID dematAccountNumber, String instrumentName) {
        return dematAccounts.stream().
                filter(dematAccount -> dematAccount.getDematAccountNumber() == dematAccountNumber).
                findAny().orElse(null);
    }

    @Override
    public void add(DematAccount dematAccount) {
        this.dematAccounts.add(dematAccount);
    }
}
