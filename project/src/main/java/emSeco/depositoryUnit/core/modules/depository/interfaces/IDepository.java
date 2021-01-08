package emSeco.depositoryUnit.core.modules.depository.interfaces;

import emSeco.shared.architecturalConstructs.BooleanResultMessage;

import java.util.UUID;

public interface IDepository {
    void setDepositoryInfo(UUID depositoryId);
    UUID getDepositoryId();
    BooleanResultMessage debit_API(UUID dematAccountNumber, String instrumentName, int quantity);
    BooleanResultMessage credit_API(UUID dematAccountNumber, String instrumentName, int quantity);
    BooleanResultMessage checkBalance_API(UUID dematAccountNumber, String instrumentName, int quantity);
}
