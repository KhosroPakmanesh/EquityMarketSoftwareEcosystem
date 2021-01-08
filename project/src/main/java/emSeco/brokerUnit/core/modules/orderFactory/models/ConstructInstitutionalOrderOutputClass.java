package emSeco.brokerUnit.core.modules.orderFactory.models;

import emSeco.brokerUnit.core.entities.order.InstitutionalOrder;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;


import java.util.ArrayList;
import java.util.List;

public class ConstructInstitutionalOrderOutputClass {
    private final List<BooleanResultMessage> orderValidationResultMessages;
    private final List<BooleanResultMessage> clientComplianceCheckingResultMessages;
    private final List<BooleanResultMessage> governmentalComplianceCheckingResultMessages;
    private final List<BooleanResultMessage> riskManagementResultMessages;
    private final InstitutionalOrder institutionalOrder;

    public ConstructInstitutionalOrderOutputClass
            (List<BooleanResultMessage> orderValidationResultMessages,
             List<BooleanResultMessage> clientComplianceCheckingResultMessages,
             List<BooleanResultMessage> governmentalComplianceCheckingResultMessages,
             List<BooleanResultMessage> riskManagementResultMessages,
             InstitutionalOrder institutionalOrder) {
        this.orderValidationResultMessages = orderValidationResultMessages;
        this.clientComplianceCheckingResultMessages = clientComplianceCheckingResultMessages;
        this.governmentalComplianceCheckingResultMessages = governmentalComplianceCheckingResultMessages;
        this.riskManagementResultMessages = riskManagementResultMessages;
        this.institutionalOrder = institutionalOrder;
    }


    public InstitutionalOrder getInstitutionalOrder() {
        return institutionalOrder;
    }
    public boolean hasErrors() {
        return (orderValidationResultMessages.size() > 0 ||
                clientComplianceCheckingResultMessages.size() >0 ||
                governmentalComplianceCheckingResultMessages.size() >0 ||
                riskManagementResultMessages.size()> 0);
    }

    public List<BooleanResultMessage> getOrderConstructionResultMessages(){
        List<BooleanResultMessage> allResultMessages= new ArrayList<>();

        allResultMessages.addAll(orderValidationResultMessages);
        allResultMessages.addAll(clientComplianceCheckingResultMessages);
        allResultMessages.addAll(governmentalComplianceCheckingResultMessages);
        allResultMessages.addAll(riskManagementResultMessages);

        return allResultMessages;
    }
}
