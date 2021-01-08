package emSeco.exchangeUnit.core.modules.orderValidationRules.sharedOrderValidationRules.implementations;

//#if ExchangeSharedOrderTermInformationValidation
import emSeco.exchangeUnit.core.entities.order.Order;
import emSeco.exchangeUnit.core.modules.orderValidationRules.sharedOrderValidationRules.interfaces.ISharedOrderValidationRule;
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

        if (order.getTerm().getPrice() <= 0) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The price field is empty or its value is invalid!"));
        }

        if (order.getTerm().getInstrumentName().equals("")) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The instrument name field is empty or its value is invalid!"));
        }

        return new BooleanResultMessage
                (true, OperationMessage.emptyOperationMessage());
    }
}
//#endif
