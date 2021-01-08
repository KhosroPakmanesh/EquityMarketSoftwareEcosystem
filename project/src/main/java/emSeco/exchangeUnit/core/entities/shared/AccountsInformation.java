package emSeco.exchangeUnit.core.entities.shared;

import java.util.UUID;

public class AccountsInformation {
    private final UUID clearingBankId;
    private final UUID clearingBankAccountNumber;
    private final UUID depositoryId;
    private final UUID dematAccountNumber;

    public AccountsInformation(UUID clearingBankId, UUID clearingBankAccountNumber,
                               UUID depositoryId, UUID dematAccountNumber) {
        this.clearingBankId = clearingBankId;
        this.clearingBankAccountNumber = clearingBankAccountNumber;
        this.depositoryId = depositoryId;
        this.dematAccountNumber = dematAccountNumber;
    }

    public UUID getClearingBankId() {
        return clearingBankId;
    }

    public UUID getClearingBankAccountNumber() {
        return clearingBankAccountNumber;
    }

    public UUID getDepositoryId() {
        return depositoryId;
    }

    public UUID getDematAccountNumber() {
        return dematAccountNumber;
    }
}
