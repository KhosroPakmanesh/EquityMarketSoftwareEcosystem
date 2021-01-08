package emSeco.clearingCropUnit.core.modules.moneyAndEquityTransferUnit.models;

import emSeco.clearingCropUnit.core.entities.settlementResult.SettlementResult;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;

import java.util.List;

public class NovateMoneyAndSharesOutputClass {
    private final List<SettlementResult> settlementResults;
    private final BooleanResultMessage transferResultMessage;

    public NovateMoneyAndSharesOutputClass(BooleanResultMessage transferResultMessage
            , List<SettlementResult> settlementResults) {
        this.transferResultMessage = transferResultMessage;
        this.settlementResults = settlementResults;
    }

    public List<SettlementResult> getSettlementResults() {
        return settlementResults;
    }

    public BooleanResultMessage getTransferResultMessage() {
        return transferResultMessage;
    }
}
