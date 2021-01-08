package emSeco.brokerUnit.core.modules.brokerEquityTransferMethods.interfaces;

import emSeco.brokerUnit.core.entities.shared.EquityTransferMethod;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;


import java.util.UUID;

public interface IBrokerEquityTransferMethod {
    EquityTransferMethod getEquityTransferMethod();

    BooleanResultMessage transferFromClientToBroker (
            UUID brokerDepositoryId, UUID brokerDematAccountNumber,
            UUID clientDepositoryId, UUID clientDematAccountNumber,
            UUID brokerInternalDematAccountNumber,
            UUID clientTradeCode, String instrumentName, int getQuantity);

    BooleanResultMessage transferFromBrokerToClient (
            UUID brokerDepositoryId, UUID brokerDematAccountNumber,
            UUID clientDepositoryId, UUID clientDematAccountNumber,
            UUID brokerInternalDematAccountNumber,
            UUID clientTradeCode, String instrumentName, int getQuantity);
}
