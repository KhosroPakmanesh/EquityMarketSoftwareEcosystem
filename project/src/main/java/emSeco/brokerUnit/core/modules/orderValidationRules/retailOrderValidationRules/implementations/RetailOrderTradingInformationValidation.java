package emSeco.brokerUnit.core.modules.orderValidationRules.retailOrderValidationRules.implementations;

//#if BrokerRetailOrderTradingInformationValidation
import emSeco.brokerUnit.core.entities.order.RetailOrder;
import emSeco.brokerUnit.core.modules.orderValidationRules.retailOrderValidationRules.interfaces.IRetailOrderValidationRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;

public class RetailOrderTradingInformationValidation implements IRetailOrderValidationRule {
    public BooleanResultMessage checkRule(RetailOrder RetailOrder) {
        if (RetailOrder.getClientTradingCode() == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The client trading code field is empty or its value is invalid!"));
        }

        return new BooleanResultMessage
                (true,OperationMessage.emptyOperationMessage());
    }
}
//#endif
