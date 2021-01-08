package emSeco.brokerUnit.core.modules.broker.models;

import emSeco.shared.architecturalConstructs.BooleanResultMessage;


import java.util.ArrayList;
import java.util.List;

public class SubmitAllocationDetailsOutputClass {
    private List<BooleanResultMessage> allocationDetailConstructionResultMessages;

    public SubmitAllocationDetailsOutputClass() {
        this.allocationDetailConstructionResultMessages = new ArrayList<>();
    }

    public void setAllocationDetailConstructionResultMessages
            (List<BooleanResultMessage> allocationDetailConstructionResultMessages) {
        this.allocationDetailConstructionResultMessages = allocationDetailConstructionResultMessages;
    }
}
