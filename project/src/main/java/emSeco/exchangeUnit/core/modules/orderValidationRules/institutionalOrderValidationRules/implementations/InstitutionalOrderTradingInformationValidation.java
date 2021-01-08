package emSeco.exchangeUnit.core.modules.orderValidationRules.institutionalOrderValidationRules.implementations;

//#if ExchangeInstitutionalOrderTradingInformationValidation
import emSeco.exchangeUnit.core.entities.order.Order;
import emSeco.exchangeUnit.core.modules.orderValidationRules.institutionalOrderValidationRules.interfaces.IInstitutionalOrderValidationRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;

public class InstitutionalOrderTradingInformationValidation implements IInstitutionalOrderValidationRule {
    @Override
    public BooleanResultMessage checkRule(Order institutionalOrder) {

        if (institutionalOrder.getTradingInformation().getRegisteredCode() == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The registered code field is empty or its value is invalid!"));
        }

        return new BooleanResultMessage
                (true, OperationMessage.emptyOperationMessage());
    }
}
//#endif
