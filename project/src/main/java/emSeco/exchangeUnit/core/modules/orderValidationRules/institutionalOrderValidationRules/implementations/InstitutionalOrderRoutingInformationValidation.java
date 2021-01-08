package emSeco.exchangeUnit.core.modules.orderValidationRules.institutionalOrderValidationRules.implementations;

//#if ExchangeInstitutionalOrderRoutingInformationValidation
import emSeco.exchangeUnit.core.entities.order.Order;
import emSeco.exchangeUnit.core.modules.orderValidationRules.institutionalOrderValidationRules.interfaces.IInstitutionalOrderValidationRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;

public class InstitutionalOrderRoutingInformationValidation implements IInstitutionalOrderValidationRule {
    @Override
    public BooleanResultMessage checkRule(Order institutionalOrder) {
        if (institutionalOrder.getRoutingInformation().getCustodianId() == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The custodian id field is empty or its value is invalid!"));
        }

        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }
}
//#endif
