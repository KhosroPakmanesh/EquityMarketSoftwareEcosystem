package emSeco.exchangeUnit.core.modules.orderValidationRules.sharedOrderValidationRules.implementations;

//#if ExchangeSharedOrderRoutingInformationValidation
import emSeco.exchangeUnit.core.entities.order.Order;
import emSeco.exchangeUnit.core.modules.orderValidationRules.sharedOrderValidationRules.interfaces.ISharedOrderValidationRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;

public class SharedOrderRoutingInformationValidation implements ISharedOrderValidationRule {
    @Override
    public BooleanResultMessage checkRule(Order order) {
        if (order.getRoutingInformation().getBrokerId() == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The brokerId field is empty or its value is invalid!"));
        }

        return new BooleanResultMessage
                (true, OperationMessage.emptyOperationMessage());
    }
}
//#endif
