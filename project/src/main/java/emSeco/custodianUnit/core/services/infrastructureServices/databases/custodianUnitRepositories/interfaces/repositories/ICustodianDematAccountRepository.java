package emSeco.custodianUnit.core.services.infrastructureServices.databases.custodianUnitRepositories.interfaces.repositories;

import emSeco.custodianUnit.core.entities.custodianDematAccount.CustodianDematAccount;

import java.util.UUID;

public interface ICustodianDematAccountRepository {
    void add(CustodianDematAccount custodianDematAccount);
    CustodianDematAccount get(UUID custodianDematAccountNumber);
}
