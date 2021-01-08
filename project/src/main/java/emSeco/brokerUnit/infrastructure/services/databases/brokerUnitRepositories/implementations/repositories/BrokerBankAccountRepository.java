package emSeco.brokerUnit.infrastructure.services.databases.brokerUnitRepositories.implementations.repositories;

import emSeco.brokerUnit.core.entities.brokerBankAccount.BrokerBankAccount;
import emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces.repositories.IBrokerBankAccountRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BrokerBankAccountRepository implements IBrokerBankAccountRepository {
    private final List<BrokerBankAccount> brokerBankAccounts;

    public BrokerBankAccountRepository() {
        this.brokerBankAccounts = new ArrayList<>();
    }

    @Override
    public void add(BrokerBankAccount brokerBankAccount) {
        this.brokerBankAccounts.add(brokerBankAccount);
    }

    @Override
    public BrokerBankAccount get(UUID brokerBankAccountNumber) {
        return brokerBankAccounts.stream().
                filter(brokerBankAccount -> brokerBankAccount.getBrokerBankAccountNumber()
                        == brokerBankAccountNumber).findAny().orElse(null);
    }
}
