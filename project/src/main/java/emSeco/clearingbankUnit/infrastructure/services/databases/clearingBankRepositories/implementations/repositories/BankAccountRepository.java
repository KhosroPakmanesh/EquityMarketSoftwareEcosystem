package emSeco.clearingbankUnit.infrastructure.services.databases.clearingBankRepositories.implementations.repositories;

import emSeco.clearingbankUnit.core.entities.bankAccount.BankAccount;
import emSeco.clearingbankUnit.core.services.infrastructureServices.databases.clearingBankRepositories.interfaces.repositories.IBankAccountRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BankAccountRepository implements IBankAccountRepository {
    private final List<BankAccount> bankAccounts;

    public BankAccountRepository() {
        bankAccounts = new ArrayList<>();
    }

    @Override
    public BankAccount get(UUID bankAccountNumber) {
        return this.bankAccounts.stream().
                filter(bankAccount -> bankAccount.getBrokerBankAccountNumber() == bankAccountNumber).
                findAny().orElse(null);
    }

    @Override
    public void add(BankAccount bankAccount) {
        this.bankAccounts.add(bankAccount);
    }
}
