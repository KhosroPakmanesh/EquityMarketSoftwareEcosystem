package emSeco.brokerUnit.core.modules.brokerMoneyTransferMethods.interfaces;

import emSeco.brokerUnit.core.entities.shared.MoneyTransferMethod;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;


import java.util.UUID;

public interface IBrokerMoneyTransferMethod {
    MoneyTransferMethod getMoneyTransferMethod();

    BooleanResultMessage transferFromClientToBroker
            (UUID brokerClearingBankId, UUID brokerClearingBankAccountNumber,
             UUID clientClearingBankId, UUID clientClearingBankAccountNumber,
             UUID internalBankAccountNumber,UUID clientTradeCode,double totalPrice);

    BooleanResultMessage transferFromBrokerToClient
            (UUID brokerClearingBankId, UUID brokerClearingBankAccountNumber,
             UUID clientClearingBankId, UUID clientClearingBankAccountNumber,
             UUID internalBankAccountNumber,UUID clientTradeCode, double totalPrice);
}
