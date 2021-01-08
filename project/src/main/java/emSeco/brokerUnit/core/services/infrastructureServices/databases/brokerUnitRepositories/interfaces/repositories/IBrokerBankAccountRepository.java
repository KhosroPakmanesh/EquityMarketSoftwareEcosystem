package emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces.repositories;

import emSeco.brokerUnit.core.entities.brokerBankAccount.BrokerBankAccount;

import java.util.UUID;

public interface IBrokerBankAccountRepository {
    void add(BrokerBankAccount brokerBankAccount);
    BrokerBankAccount get(UUID brokerBankAccountNumber);
}
