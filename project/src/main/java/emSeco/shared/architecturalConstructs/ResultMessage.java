package emSeco.shared.architecturalConstructs;

public class ResultMessage<T>
{
    private final T operationResult;
    private final OperationMessage operationMessage;

    public ResultMessage(T operationResult, OperationMessage operationMessage)
    {
        this.operationResult = operationResult;
        this.operationMessage = operationMessage;
    }

    public T getOperationResult() {
        return operationResult;
    }

    public OperationMessage getOperationMessage() {
        return operationMessage;
    }
}
