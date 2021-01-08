package emSeco.clearingCropUnit.core.services.infrastructureServices.gateways.clearingCorpApiGateway.interfaces.gateways;

import emSeco.shared.architecturalConstructs.BooleanResultMessage;

import java.util.UUID;

public interface IClearingCorpToClearingBankUnitApiGateway {
    BooleanResultMessage debit(UUID clearingBankId, UUID clearingBankAccountNumber, double totalPrice);

    BooleanResultMessage credit(UUID clearingBankId, UUID clearingBankAccountNumber, double totalPrice);

    BooleanResultMessage checkBalance
            (UUID clearingBankId, UUID clearingBankAccountNumber, double totalAmount);
}
