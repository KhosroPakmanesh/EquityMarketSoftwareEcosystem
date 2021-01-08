package emSeco.brokerUnit.core.services.infrastructureServices.gateways.brokerUnitApiGateways.interfaces.gateways;

import emSeco.shared.architecturalConstructs.BooleanResultMessage;


import java.util.UUID;

public interface IBrokerToClearingBankUnitApiGateway {
    BooleanResultMessage debit(UUID clearingBankId, UUID clearingBankAccountNumber, double totalPrice);
    BooleanResultMessage credit(UUID clearingBankId, UUID clearingBankAccountNumber, double totalPrice);
    BooleanResultMessage checkBalance(UUID clearingBankId, UUID clearingBankAccountNumber, double totalPrice);
}
