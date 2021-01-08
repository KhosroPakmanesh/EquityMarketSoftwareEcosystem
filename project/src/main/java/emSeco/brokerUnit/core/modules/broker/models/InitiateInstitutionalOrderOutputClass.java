package emSeco.brokerUnit.core.modules.broker.models;

import emSeco.shared.architecturalConstructs.BooleanResultMessage;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InitiateInstitutionalOrderOutputClass {
    private UUID orderId;
    private List<BooleanResultMessage> orderConstructionResultMessages;
    private Boolean orderSubmitted;

    public InitiateInstitutionalOrderOutputClass() {
        this.orderConstructionResultMessages = new ArrayList<>();
        orderSubmitted= false;
    }

    public List<BooleanResultMessage> getOrderConstructionResultMessages() {
        return orderConstructionResultMessages;
    }
    public void setOrderConstructionResultMessages
            (List<BooleanResultMessage> orderConstructionResultMessages) {
        this.orderConstructionResultMessages=orderConstructionResultMessages;
    }

    public UUID getOrderId() {
        return orderId;
    }
    public void setOrderId(UUID orderId) {

        this.orderId = orderId;
    }

    public Boolean isOrderSubmitted() {
        return orderSubmitted;
    }
    public void orderSubmitted() {
        orderSubmitted=true;
    }
}
