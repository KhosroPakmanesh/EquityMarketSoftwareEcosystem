package emSeco.clearingCropUnit.core.services.infrastructureServices.gateways.clearingCorpApiGateway.interfaces.gateways;

import emSeco.shared.architecturalConstructs.BooleanResultMessage;

import java.util.UUID;

public interface IClearingCorpToDepositoryUnitApiGateway {
    BooleanResultMessage debit(UUID depositoryId, UUID dematAccountNumber, String instrumentName, int quantity);

    BooleanResultMessage credit(UUID depositoryId, UUID dematAccountNumber, String instrumentName, int quantity);

    BooleanResultMessage checkBalance
            (UUID depositoryId, UUID dematAccountNumber, String instrumentName, int quantity);
}
