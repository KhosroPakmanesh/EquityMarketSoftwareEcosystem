package emSeco.brokerUnit.infrastructure.services.databases.brokerUnitRepositories.implementations.repositories;

import emSeco.brokerUnit.core.entities.brokerDematAccount.BrokerDematAccount;
import emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces.repositories.IBrokerDematAccountRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BrokerDematAccountRepository implements IBrokerDematAccountRepository {
    private final List<BrokerDematAccount> brokerDematAccounts;

    public BrokerDematAccountRepository() {
        this.brokerDematAccounts = new ArrayList<>();
    }

    @Override
    public void add(BrokerDematAccount brokerDematAccount) {
        brokerDematAccounts.add(brokerDematAccount);
    }

    @Override
    public BrokerDematAccount get(UUID brokerDematAccountNumber) {
        return brokerDematAccounts.stream().
                filter(brokerDematAccount -> brokerDematAccount.getBrokerBankAccountNumber() ==
                        brokerDematAccountNumber).findAny().orElse(null);
    }
}
