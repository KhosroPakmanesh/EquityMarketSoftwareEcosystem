package emSeco.custodianUnit.core.entities.custodianUnitInfo;


import emSeco.custodianUnit.core.entities.shared.AccountsInformation;

import java.util.UUID;

public class CustodianUnitInfo {
    private final UUID custodianId;

    private final AccountsInformation accountsInformation;

    public CustodianUnitInfo(UUID custodianId,AccountsInformation accountsInformation) {
        this.custodianId = custodianId;
        this.accountsInformation = accountsInformation;
    }


    public UUID getCustodianId() {
        return custodianId;
    }

    public AccountsInformation getAccountsInformation() {
        return accountsInformation;
    }
}
