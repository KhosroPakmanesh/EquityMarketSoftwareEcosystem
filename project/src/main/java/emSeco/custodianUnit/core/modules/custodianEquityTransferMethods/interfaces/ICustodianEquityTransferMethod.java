package emSeco.custodianUnit.core.modules.custodianEquityTransferMethods.interfaces;

import emSeco.custodianUnit.core.entities.shared.EquityTransferMethod;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;


import java.util.UUID;

public interface ICustodianEquityTransferMethod {
    EquityTransferMethod getEquityTransferMethod();

    BooleanResultMessage transferFromClientToCustodian(
            UUID custodianDepositoryId, UUID custodianDematAccountNumber,
            UUID clientDepositoryId, UUID clientDematAccountNumber,
            UUID internalDematAccountNumber,UUID clientInternalDematAccountNumber,
            String instrumentName, int quantity);

    BooleanResultMessage transferFromCustodianToClient (
            UUID custodianDepositoryId, UUID custodianDematAccountNumber,
            UUID clientDepositoryId, UUID clientDematAccountNumber,
            UUID custodianInternalDematAccountNumber,UUID clientInternalDematAccountNumber,
            String instrumentName, int getQuantity);
}
