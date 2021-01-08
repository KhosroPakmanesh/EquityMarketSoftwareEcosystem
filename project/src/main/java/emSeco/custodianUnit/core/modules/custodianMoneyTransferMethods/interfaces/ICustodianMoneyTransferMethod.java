package emSeco.custodianUnit.core.modules.custodianMoneyTransferMethods.interfaces;

import emSeco.custodianUnit.core.entities.shared.MoneyTransferMethod;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;


import java.util.UUID;

public interface ICustodianMoneyTransferMethod {
    MoneyTransferMethod getMoneyTransferMethod();

    BooleanResultMessage transferFromClientToCustodian
            (UUID custodianClearingBankId, UUID custodianClearingBankAccountNumber,
             UUID clientClearingBankId, UUID clientClearingBankAccountNumber,
             UUID custodianInternalBankAccountNumber,UUID clientInternalBankAccountNumber, double paymentAmount);

    BooleanResultMessage transferFromCustodianToClient
            (UUID custodianClearingBankId, UUID custodianClearingBankAccountNumber,
             UUID clientClearingBankId, UUID clientClearingBankAccountNumber,
             UUID custodianInternalBankAccountNumber,UUID clientTradeCode, double totalPrice);
}
