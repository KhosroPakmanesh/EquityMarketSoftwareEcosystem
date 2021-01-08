package emSeco.exchangeUnit.core.modules.orderFactory.implementations;

import com.google.inject.Inject;
import emSeco.exchangeUnit.core.entities.order.Order;
import emSeco.exchangeUnit.core.entities.shared.InitiatorType;
import emSeco.exchangeUnit.core.modules.orderFactory.interfaces.IOrderFactory;
import emSeco.exchangeUnit.core.modules.orderFactory.models.ConstructOrderOutputClass;
import emSeco.exchangeUnit.core.modules.orderValidator.interfaces.IOrderValidator;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;

import java.util.ArrayList;
import java.util.List;

public class OrderFactory implements IOrderFactory {
    private final IOrderValidator orderValidator;

    @Inject
    public OrderFactory(IOrderValidator orderValidator) {
        this.orderValidator = orderValidator;
    }

    @Override
    public ConstructOrderOutputClass constructOrder(Order order) {
        List<BooleanResultMessage> orderValidationResultMessages = new ArrayList<>();

        if (order.getInitiatorType() == InitiatorType.institutional) {
            orderValidationResultMessages = orderValidator.validateInstitutionalOrder(order);

            return new ConstructOrderOutputClass
                    (orderValidationResultMessages, order);
        } else if (order.getInitiatorType() == InitiatorType.retail) {
            orderValidationResultMessages = orderValidator.validateRetailOrder(order);

            return new ConstructOrderOutputClass
                    (orderValidationResultMessages, order);
        } else {
            return new ConstructOrderOutputClass(
                    new ArrayList<BooleanResultMessage>() {{
                        add(new BooleanResultMessage(
                                false, OperationMessage.Create("The order is invalid!")));
                    }}, null);
        }
    }
}