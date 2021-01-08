package emSeco.brokerUnit.core.modules.orderValidationRules.sharedOrderValidationRules.implementations;

//#if BrokerSharedOrderTermInformationValidation
import emSeco.brokerUnit.core.entities.order.Order;
import emSeco.brokerUnit.core.modules.orderValidationRules.sharedOrderValidationRules.interfaces.ISharedOrderValidationRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;

public class SharedOrderTermInformationValidation implements ISharedOrderValidationRule {
    @Override
    public BooleanResultMessage checkRule(Order order) {
        if (order.getTerm().getQuantity() <= 0) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The quantity field is empty or its value is invalid!"));
        }

        return new BooleanResultMessage
                (true, OperationMessage.emptyOperationMessage());
    }
}
//#endif
