package emSeco.custodianUnit.core.services.infrastructureServices.gateways.custodianUnitApiGateway.interfaces.gateways;

import emSeco.shared.architecturalConstructs.BooleanResultMessage;


import java.util.UUID;

public interface ICustodianToDepositoryUnitApiGateway {
    BooleanResultMessage debit(UUID depositoryId, UUID dematAccountNumber, String instrumentName, int quantity);
    BooleanResultMessage credit(UUID depositoryId, UUID dematAccountNumber, String instrumentName, int quantity);
    BooleanResultMessage checkBalance
            (UUID depositoryId, UUID dematAccountNumber,String instrumentName, int quantity);
}
