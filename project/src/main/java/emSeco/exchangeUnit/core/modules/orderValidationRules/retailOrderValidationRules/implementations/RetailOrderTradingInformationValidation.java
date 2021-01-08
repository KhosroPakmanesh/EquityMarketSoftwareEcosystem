package emSeco.exchangeUnit.core.modules.orderValidationRules.retailOrderValidationRules.implementations;

//#if ExchangeRetailOrderTradingInformationValidation
import emSeco.exchangeUnit.core.entities.order.Order;
import emSeco.exchangeUnit.core.modules.orderValidationRules.retailOrderValidationRules.interfaces.IRetailOrderValidationRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;

public class RetailOrderTradingInformationValidation implements IRetailOrderValidationRule {
    public BooleanResultMessage checkRule(Order RetailOrder) {
        if (RetailOrder.getTradingInformation().getClientTradingCode() == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The client trading code field is empty or its value is invalid!"));
        }

        return new BooleanResultMessage
                (true,OperationMessage.emptyOperationMessage());
    }
}
//#endif
