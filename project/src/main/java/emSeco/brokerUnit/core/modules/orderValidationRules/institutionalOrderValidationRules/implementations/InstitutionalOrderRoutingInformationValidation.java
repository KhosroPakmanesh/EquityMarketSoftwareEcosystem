package emSeco.brokerUnit.core.modules.orderValidationRules.institutionalOrderValidationRules.implementations;

//#if BrokerInstitutionalOrderRoutingInformationValidation
import emSeco.brokerUnit.core.entities.order.InstitutionalOrder;
import emSeco.brokerUnit.core.modules.orderValidationRules.institutionalOrderValidationRules.interfaces.IInstitutionalOrderValidationRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;

public class InstitutionalOrderRoutingInformationValidation implements IInstitutionalOrderValidationRule {
    @Override
    public BooleanResultMessage checkRule(InstitutionalOrder institutionalOrder) {
        if (institutionalOrder.getRoutingInformation().getCustodianId() == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The custodian id field is empty or its value is invalid!"));
        }

        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }
}
//#endif
