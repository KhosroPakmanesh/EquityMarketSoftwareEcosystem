package emSeco.clearingbankUnit.core.modules.clearingBank.interfaces;

import emSeco.shared.architecturalConstructs.BooleanResultMessage;

import java.util.UUID;

public interface IClearingBank {
    void setClearingBankUnitInfo(UUID clearingBankId);
    UUID getClearingBankId();
    BooleanResultMessage debit_API(UUID clearingBankAccountNumber, double totalAmount);
    BooleanResultMessage credit_API(UUID clearingBankAccountNumber, double totalAmount);
    BooleanResultMessage checkBalance_API(UUID clearingBankAccountNumber, double totalAmount);
}
