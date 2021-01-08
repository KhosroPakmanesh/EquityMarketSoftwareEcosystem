package emSeco.depositoryUnit.core.modules.depository.implementations;

import com.google.inject.Inject;
import emSeco.depositoryUnit.core.entities.dematAccount.DematAccount;
import emSeco.depositoryUnit.core.modules.depository.interfaces.IDepository;
import emSeco.depositoryUnit.core.services.infrastructureServices.databases.depositoryRepositories.interfaces.IDepositoryUnitRepositories;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;


import java.util.UUID;

public class Depository implements IDepository {
    private UUID depositoryId;
    private final IDepositoryUnitRepositories depositoryUnitRepositories;

    @Inject
    public Depository(IDepositoryUnitRepositories depositoryUnitRepositories) {
        this.depositoryUnitRepositories = depositoryUnitRepositories;
    }

    public void setDepositoryInfo(UUID depositoryId) {
        this.depositoryId = depositoryId;
    }

    @Override
    public UUID getDepositoryId() {
        return depositoryId;
    }

    @Override
    public BooleanResultMessage debit_API(UUID dematAccountNumber, String instrumentName, int quantity) {
        DematAccount foundDematAccount = depositoryUnitRepositories.
                getDematAccountRepository().get(dematAccountNumber, instrumentName);

        if (foundDematAccount == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.Create("Depository Demat account not found!"));
        }

        if (!foundDematAccount.hasEnoughBalance(instrumentName, quantity)) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("Depository Demat account does not have have enough balance!"));
        }

        foundDematAccount.debit(instrumentName, quantity);
        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }

    @Override
    public BooleanResultMessage credit_API(UUID dematAccountNumber, String instrumentName, int quantity) {
        DematAccount foundDematAccount = depositoryUnitRepositories.
                getDematAccountRepository().get(dematAccountNumber, instrumentName);

        if (foundDematAccount == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.Create("Depository Demat account not found!"));
        }

        foundDematAccount.credit(instrumentName, quantity);
        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }

    @Override
    public BooleanResultMessage checkBalance_API(UUID dematAccountNumber, String instrumentName, int quantity) {
        DematAccount foundDematAccount = depositoryUnitRepositories.
                getDematAccountRepository().get(dematAccountNumber, instrumentName);

        if (foundDematAccount == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.Create("Depository Demat account not found!"));
        }

        boolean hasEnoughBalance = foundDematAccount.hasEnoughBalance(instrumentName, quantity);
        if (hasEnoughBalance) {
            return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
        } else {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("Depository Demat account does not have have enough balance!"));
        }
    }
}
