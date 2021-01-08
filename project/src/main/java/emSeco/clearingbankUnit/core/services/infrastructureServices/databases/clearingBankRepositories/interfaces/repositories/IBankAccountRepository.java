package emSeco.clearingbankUnit.core.services.infrastructureServices.databases.clearingBankRepositories.interfaces.repositories;

import emSeco.clearingbankUnit.core.entities.bankAccount.BankAccount;

import java.util.UUID;

public interface IBankAccountRepository {
    BankAccount get(UUID bankAccountNumber);
    void add(BankAccount bankAccount);
}
