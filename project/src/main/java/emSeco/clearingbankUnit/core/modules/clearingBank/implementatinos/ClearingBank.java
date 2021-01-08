package emSeco.clearingbankUnit.core.modules.clearingBank.implementatinos;

import com.google.inject.Inject;
import emSeco.clearingbankUnit.core.entities.bankAccount.BankAccount;
import emSeco.clearingbankUnit.core.modules.clearingBank.interfaces.IClearingBank;
import emSeco.clearingbankUnit.core.services.infrastructureServices.databases.clearingBankRepositories.interfaces.IClearingBankUnitRepositories;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;

import java.util.UUID;

public class ClearingBank implements IClearingBank {
    private UUID clearingBankId;
    private final IClearingBankUnitRepositories clearingBankUnitRepositories;

    @Inject
    public ClearingBank(IClearingBankUnitRepositories clearingBankUnitRepositories) {
        this.clearingBankUnitRepositories = clearingBankUnitRepositories;
    }

    @Override
    public void setClearingBankUnitInfo(UUID clearingBankId) {
        this.clearingBankId = clearingBankId;
    }

    @Override
    public UUID getClearingBankId() {
        return clearingBankId;
    }

    @Override
    public BooleanResultMessage debit_API(UUID clearingBankAccountNumber, double totalAmount) {
        BankAccount foundBankAccount = clearingBankUnitRepositories.
                getBankAccountRepository().get(clearingBankAccountNumber);

        if (foundBankAccount == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.Create("Clearing bank account not found!"));
        }

        if (!foundBankAccount.HasEnoughBalance(totalAmount)) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("Clearing bank account does not have have enough balance!"));
        }

        foundBankAccount.debit(totalAmount);
        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }

    @Override
    public BooleanResultMessage credit_API(UUID clearingBankAccountNumber, double totalAmount) {
        BankAccount foundBankAccount = clearingBankUnitRepositories.
                getBankAccountRepository().get(clearingBankAccountNumber);

        if (foundBankAccount == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.Create("Clearing bank account not found!"));
        }

        foundBankAccount.credit(totalAmount);
        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }

    @Override
    public BooleanResultMessage checkBalance_API(UUID clearingBankAccountNumber, double totalAmount) {
        BankAccount foundBankAccount = clearingBankUnitRepositories.
                getBankAccountRepository().get(clearingBankAccountNumber);

        if (foundBankAccount == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.Create("Clearing bank account not found!"));
        }

        boolean hasEnoughBalance = foundBankAccount.HasEnoughBalance(totalAmount);
        if (hasEnoughBalance) {
            return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
        } else {
            return new BooleanResultMessage(false,
                    OperationMessage.
                            Create("Clearing bank account does not have have enough balance!"));
        }
    }
}
