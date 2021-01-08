package emSeco.shared.architecturalConstructs;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BooleanResultMessages extends ResultMessages<Boolean>{

    public BooleanResultMessages(Boolean operationResult, List<OperationMessage> operationMessages) {
        super(operationResult, operationMessages);
    }

    public static BooleanResultMessages aggregateListOfResultMessage
            (List<BooleanResultMessage> listOfResultMessage) {
        List<BooleanResultMessage> falseListOfResultMessage = listOfResultMessage.stream().
                filter(resultMessage -> !resultMessage.getOperationResult())
                .collect(Collectors.toList());

        if (falseListOfResultMessage.size() > 0) {
            List<OperationMessage> operationMessages = falseListOfResultMessage.stream()
                    .map(ResultMessage::getOperationMessage).
                            collect(Collectors.toList());

            return new BooleanResultMessages(false, operationMessages);
        }

        return new BooleanResultMessages(true, new ArrayList<>());
    }

    public static BooleanResultMessages aggregateListOfResultMessages
            (List<BooleanResultMessages> listOfResultMessages) {

        List<BooleanResultMessages> falseResultMessages = listOfResultMessages.stream().
                filter(resultMessages -> !resultMessages.getOperationResult())
                .collect(Collectors.toList());

        if (falseResultMessages.size() > 0) {
            List<OperationMessage> operationMessages = falseResultMessages.stream().
                    map(ResultMessages::getOperationMessages).collect(Collectors.toList()).
                    stream().flatMap(List::stream).collect(Collectors.toList());

            return new BooleanResultMessages(false, operationMessages);
        }

        return new BooleanResultMessages(true, new ArrayList<>());
    }
}
