package emSeco.custodianUnit.core.entities.shared;

import java.util.UUID;

public class InternalAccountsInformation {
    private final UUID internalBankAccountNumber;
    private final UUID internalDematAccountNumber;


    public InternalAccountsInformation(UUID internalBankAccountNumber, UUID internalDematAccountNumber) {
        this.internalBankAccountNumber = internalBankAccountNumber;
        this.internalDematAccountNumber = internalDematAccountNumber;
    }

    public UUID getInternalBankAccountNumber() {
        return internalBankAccountNumber;
    }

    public UUID getInternalDematAccountNumber() {
        return internalDematAccountNumber;
    }
}
