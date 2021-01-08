package emSeco.clearingCropUnit.core.entities.clearingCorpUnitInfo;

import emSeco.clearingCropUnit.core.entities.shared.AccountsInformation;

public class ClearingCorpUnitInfo {
    private final AccountsInformation accountsInformation;

    public ClearingCorpUnitInfo(AccountsInformation accountsInformation) {
        this.accountsInformation = accountsInformation;
    }

    public AccountsInformation getAccountsInformation() {
        return accountsInformation;
    }
}
