package emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces.repositories;

import emSeco.brokerUnit.core.entities.brokerDematAccount.BrokerDematAccount;

import java.util.UUID;

public interface IBrokerDematAccountRepository {
    void add(BrokerDematAccount brokerDematAccount);
    BrokerDematAccount get(UUID brokerDematAccountNumber);
}
