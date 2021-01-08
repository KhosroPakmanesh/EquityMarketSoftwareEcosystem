package emSeco.brokerUnit.core.entities.brokerUnitInfo;

import emSeco.brokerUnit.core.entities.shared.AccountsInformation;

import java.util.UUID;

public class BrokerUnitInfo {
    private final UUID brokerId;
    private final AccountsInformation accountsInformation;
    public BrokerUnitInfo(UUID brokerId, AccountsInformation accountsInformation)
    {
        this.brokerId = brokerId;
        this.accountsInformation = accountsInformation;
    }

    public UUID getBrokerId() {
        return brokerId;
    }

    public AccountsInformation getAccountsInformation() {
        return accountsInformation;
    }
}
