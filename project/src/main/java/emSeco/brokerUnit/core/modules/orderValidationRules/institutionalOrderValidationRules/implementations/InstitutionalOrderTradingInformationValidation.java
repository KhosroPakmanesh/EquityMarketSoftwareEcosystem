package emSeco.brokerUnit.core.modules.orderValidationRules.institutionalOrderValidationRules.implementations;

//#if BrokerInstitutionalOrderTradingInformationValidation
import emSeco.brokerUnit.core.entities.order.InstitutionalOrder;
import emSeco.brokerUnit.core.modules.orderValidationRules.institutionalOrderValidationRules.interfaces.IInstitutionalOrderValidationRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;

public class InstitutionalOrderTradingInformationValidation implements IInstitutionalOrderValidationRule {
    @Override
    public BooleanResultMessage checkRule(InstitutionalOrder institutionalOrder) {

        if (institutionalOrder.getRegisteredCode() == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The registered code field is empty or its value is invalid!"));
        }

        return new BooleanResultMessage
                (true, OperationMessage.emptyOperationMessage());
    }
}
//#endif
