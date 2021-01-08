package emSeco.brokerUnit.core.modules.orderFactory.models;

import emSeco.brokerUnit.core.entities.order.RetailOrder;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;


import java.util.ArrayList;
import java.util.List;

public class ConstructRetailOrderOutputClass {
    private final List<BooleanResultMessage> orderValidationResultMessages;
    private final List<BooleanResultMessage> clientComplianceCheckingResultMessages;
    private final List<BooleanResultMessage> governmentalComplianceCheckingResultMessages;
    private final List<BooleanResultMessage> riskManagementResultMessages;
    private final RetailOrder retailOrder;

    public ConstructRetailOrderOutputClass(List<BooleanResultMessage> orderValidationResultMessages,
                                           List<BooleanResultMessage> clientComplianceCheckingResultMessages,
                                           List<BooleanResultMessage> governmentalComplianceCheckingResultMessages,
                                           List<BooleanResultMessage> riskManagementResultMessages,
                                           RetailOrder retailOrder) {
        this.orderValidationResultMessages = orderValidationResultMessages;
        this.clientComplianceCheckingResultMessages = clientComplianceCheckingResultMessages;
        this.governmentalComplianceCheckingResultMessages = governmentalComplianceCheckingResultMessages;
        this.riskManagementResultMessages = riskManagementResultMessages;
        this.retailOrder = retailOrder;
    }

    public RetailOrder getRetailOrder() {
        return retailOrder;
    }

    public List<BooleanResultMessage> getOrderConstructionResultMessages(){
        List<BooleanResultMessage> allResultMessages= new ArrayList<>();

        allResultMessages.addAll(orderValidationResultMessages);
        allResultMessages.addAll(clientComplianceCheckingResultMessages);
        allResultMessages.addAll(governmentalComplianceCheckingResultMessages);
        allResultMessages.addAll(riskManagementResultMessages);

        return allResultMessages;
    }

    public boolean hasErrors() {
        return (orderValidationResultMessages.size() > 0 ||
                clientComplianceCheckingResultMessages.size() >0 ||
                governmentalComplianceCheckingResultMessages.size() >0 ||
                riskManagementResultMessages.size()> 0);
    }
}
