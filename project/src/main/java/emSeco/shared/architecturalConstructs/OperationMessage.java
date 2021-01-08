package emSeco.shared.architecturalConstructs;

import java.util.Comparator;
import java.util.UUID;

public class OperationMessage implements Comparator<OperationMessage> {
    private final UUID code;
    private final String debugModeMessage;
    private final String releaseModeMessage;
    private final String comparisonId;

    private OperationMessage(UUID code, String debugModeMessage, String releaseModeMessage, String ComparisonId) {
        this.code = code;
        this.debugModeMessage = debugModeMessage;
        this.releaseModeMessage = releaseModeMessage;
        this.comparisonId = ComparisonId;
    }

    public static OperationMessage Create(String message) {
        return new OperationMessage(null, message, message, null);
    }

    public static OperationMessage emptyOperationMessage() {
        return new OperationMessage(null, "", "", null);
    }


    public UUID getCode() {
        return code;
    }

    public String getDebugModeMessage() {
        return debugModeMessage;
    }

    public String getReleaseModeMessage() {
        return releaseModeMessage;
    }

    public String getComparisonId() {
        return comparisonId;
    }

    @Override
    public int compare(OperationMessage lhsOperationMessage, OperationMessage rhsOperationMessage) {
        if (lhsOperationMessage.comparisonId.
                equals(rhsOperationMessage.comparisonId)) {
            return 0;
        }

        return -1;
    }
}

