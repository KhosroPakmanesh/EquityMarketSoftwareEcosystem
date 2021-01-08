package emSeco.shared.architecturalConstructs;
import java.util.List;

public class ResultMessages<T> {
    private final T operationResult;
    private final List<OperationMessage> operationMessages;

    public ResultMessages(T operationResult, List<OperationMessage> operationMessages) {
        this.operationResult = operationResult;
        this.operationMessages = operationMessages;
    }

    public T getOperationResult() {
        return operationResult;
    }

    public List<OperationMessage> getOperationMessages() {
        return operationMessages;
    }
}
