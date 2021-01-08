package emSeco.custodianUnit.core.services.infrastructureServices.databases.custodianUnitRepositories.interfaces.repositories;

import emSeco.custodianUnit.core.entities.custodianBankAccount.CustodianBankAccount;

import java.util.UUID;

public interface ICustodianBankAccountRepository {
    void add(CustodianBankAccount custodianBankAccount);
    CustodianBankAccount get(UUID custodianBankAccountNumber);
}
