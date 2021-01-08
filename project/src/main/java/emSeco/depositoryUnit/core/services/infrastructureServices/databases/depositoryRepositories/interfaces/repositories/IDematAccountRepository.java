package emSeco.depositoryUnit.core.services.infrastructureServices.databases.depositoryRepositories.interfaces.repositories;

import emSeco.depositoryUnit.core.entities.dematAccount.DematAccount;

import java.util.UUID;

public interface IDematAccountRepository {
    DematAccount get(UUID dematAccountNumber,String instrumentName);
    void add(DematAccount dematAccount);
}
