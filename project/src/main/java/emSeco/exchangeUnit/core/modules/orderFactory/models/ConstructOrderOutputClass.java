package emSeco.exchangeUnit.core.modules.orderFactory.models;

import emSeco.exchangeUnit.core.entities.order.Order;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;

import java.util.ArrayList;
import java.util.List;

public class ConstructOrderOutputClass {
    private final List<BooleanResultMessage> orderValidationResultMessages;
    private final Order order;

    public ConstructOrderOutputClass(List<BooleanResultMessage> orderValidationResultMessages,Order order) {
        this.orderValidationResultMessages = orderValidationResultMessages;
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public List<BooleanResultMessage> getOrderConstructionResultMessages(){
        List<BooleanResultMessage> allResultMessages= new ArrayList<>();

        allResultMessages.addAll(orderValidationResultMessages);

        return allResultMessages;
    }

    public boolean hasErrors() {
        return (orderValidationResultMessages.size() > 0);
    }
}
