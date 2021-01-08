package emSeco.clearingCropUnit.core.modules.tradeSettler.models;

import emSeco.clearingCropUnit.core.entities.settlementResult.SettlementResult;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;


import java.util.ArrayList;
import java.util.List;

public class SettleTwoSideInstitutionalTradesOutputClass {

    private List<BooleanResultMessage> tradeEvaluationResultMessages;
    private BooleanResultMessage transferResultMessage;
    private List<SettlementResult> settlementResults;

    public SettleTwoSideInstitutionalTradesOutputClass() {
        this.tradeEvaluationResultMessages = new ArrayList<>();
    }

    public void setTradeEvaluationResultMessages
            (List<BooleanResultMessage> tradeEvaluationResultMessages) {
        this.tradeEvaluationResultMessages = tradeEvaluationResultMessages;
    }

    public List<BooleanResultMessage> getTradeEvaluationResultMessages() {
        return tradeEvaluationResultMessages;
    }

    public void setTransferResultMessage(BooleanResultMessage transferResultMessage) {
        this.transferResultMessage = transferResultMessage;
    }
    public BooleanResultMessage getTransferResultMessage() {
        return transferResultMessage;
    }

    public void setSettlementResults(List<SettlementResult> settlementResults) {
        this.settlementResults = settlementResults;
    }

    public List<SettlementResult> getSettlementResults() {
        return settlementResults;
    }
}
