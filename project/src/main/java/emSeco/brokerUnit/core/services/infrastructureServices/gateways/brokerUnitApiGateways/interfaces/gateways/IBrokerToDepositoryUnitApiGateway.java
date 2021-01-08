package emSeco.brokerUnit.core.services.infrastructureServices.gateways.brokerUnitApiGateways.interfaces.gateways;

import emSeco.shared.architecturalConstructs.BooleanResultMessage;


import java.util.UUID;

public interface IBrokerToDepositoryUnitApiGateway {
    BooleanResultMessage debit(UUID depositoryId, UUID dematAccountNumber, String instrumentName, int quantity);
    BooleanResultMessage credit(UUID depositoryId, UUID dematAccountNumber, String instrumentName, int quantity);
    BooleanResultMessage checkBalance(UUID depositoryId, UUID dematAccountNumber, String instrumentName, int quantity);
}
