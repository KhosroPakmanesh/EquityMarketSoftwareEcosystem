package emSeco.shared.architecturalConstructs;

public class BooleanResultMessage extends ResultMessage<Boolean>{

    public BooleanResultMessage(Boolean operationResult, OperationMessage operationMessage) {
        super(operationResult, operationMessage);
    }
}
