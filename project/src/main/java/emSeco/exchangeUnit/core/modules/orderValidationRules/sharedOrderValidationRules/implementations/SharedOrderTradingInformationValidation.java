package emSeco.exchangeUnit.core.modules.orderValidationRules.sharedOrderValidationRules.implementations;

//#if ExchangeSharedOrderTradingInformationValidation
import com.google.inject.Inject;
import emSeco.exchangeUnit.core.entities.order.Order;
import emSeco.exchangeUnit.core.modules.orderMatchingAlgorithms.interfaces.IOrderMatchingAlgorithm;
import emSeco.exchangeUnit.core.modules.orderValidationRules.sharedOrderValidationRules.interfaces.ISharedOrderValidationRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;

import java.util.Set;

public class SharedOrderTradingInformationValidation implements ISharedOrderValidationRule {
    private final Set<IOrderMatchingAlgorithm> orderMatchingAlgorithms;

    @Inject
    public SharedOrderTradingInformationValidation(Set<IOrderMatchingAlgorithm> orderMatchingAlgorithms) {
        this.orderMatchingAlgorithms = orderMatchingAlgorithms;
    }

    @Override
    public BooleanResultMessage checkRule(Order order) {
        if (order.getTradingInformation().getInitialOrderId() == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The initialOrderId field is empty or its value is invalid!"));
        }

        if (order.getTradingInformation().getSideName() == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The side name field is empty or its value is invalid!"));
        }

        if (order.getTradingInformation().getOrderType() == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The order type field is empty or its value is invalid!"));
        } else {
            IOrderMatchingAlgorithm foundOrderMatchingAlgorithm = orderMatchingAlgorithms.stream().
                    filter(orderMatchingAlgorithm ->
                            orderMatchingAlgorithm.getOrderTypeMatchingAlgorithmName().
                                    equals(order.getTradingInformation().getOrderType())).
                    findFirst().orElse(null);

            if (foundOrderMatchingAlgorithm == null) {
                return new BooleanResultMessage
                        (false, OperationMessage.
                                Create("The specified order type is not supported by this system!"));
            }
        }

        return new BooleanResultMessage
                (true, OperationMessage.emptyOperationMessage());
    }
}
//#endif
