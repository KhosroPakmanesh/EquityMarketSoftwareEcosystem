package emSeco.custodianUnit.core.services.infrastructureServices.gateways.custodianUnitApiGateway.interfaces.gateways;

import emSeco.shared.architecturalConstructs.BooleanResultMessage;


import java.util.UUID;

public interface ICustodianToClearingBankUnitApiGateway {
    BooleanResultMessage debit(UUID clearingBankId, UUID clearingBankAccountNumber, double totalAmount);
    BooleanResultMessage credit(UUID clearingBankId, UUID clearingBankAccountNumber, double totalAmount);
    BooleanResultMessage checkBalance(UUID clearingBankId, UUID clearingBankAccountNumber,double totalAmount);
}
