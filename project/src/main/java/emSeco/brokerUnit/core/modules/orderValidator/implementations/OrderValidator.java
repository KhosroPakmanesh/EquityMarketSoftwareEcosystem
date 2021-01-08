package emSeco.brokerUnit.core.modules.orderValidator.implementations;

import com.google.inject.Inject;
import emSeco.brokerUnit.core.entities.order.InstitutionalOrder;
import emSeco.brokerUnit.core.entities.order.RetailOrder;
import emSeco.brokerUnit.core.modules.orderValidationRules.sharedOrderValidationRules.interfaces.ISharedOrderValidationRule;
import emSeco.brokerUnit.core.modules.orderValidationRules.institutionalOrderValidationRules.interfaces.IInstitutionalOrderValidationRule;
import emSeco.brokerUnit.core.modules.orderValidationRules.retailOrderValidationRules.interfaces.IRetailOrderValidationRule;
import emSeco.brokerUnit.core.modules.orderValidator.interfaces.IOrderValidator;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OrderValidator implements IOrderValidator {

    private final Set<IRetailOrderValidationRule> retailOrderValidationRules;
    private final Set<IInstitutionalOrderValidationRule> institutionalOrderValidationRules;
    private final Set<ISharedOrderValidationRule> sharedOrderValidationRules;

    @Inject
    public OrderValidator(Set<IRetailOrderValidationRule> retailOrderValidationRules,
                          Set<IInstitutionalOrderValidationRule> institutionalOrderValidationRules,
                          Set<ISharedOrderValidationRule> sharedOrderValidationRules) {
        this.retailOrderValidationRules = retailOrderValidationRules;
        this.institutionalOrderValidationRules = institutionalOrderValidationRules;
        this.sharedOrderValidationRules = sharedOrderValidationRules;
    }

    public List<BooleanResultMessage> validateRetailOrder(RetailOrder retailOrder){
        List<BooleanResultMessage> resultMessages= new ArrayList<>();

        for (IRetailOrderValidationRule retailOrderValidationRule:retailOrderValidationRules) {
            BooleanResultMessage validationResultMessage =
                    retailOrderValidationRule.checkRule(retailOrder);
            if (!validationResultMessage.getOperationResult()){
                resultMessages.add(validationResultMessage);
            }
        }

        for (ISharedOrderValidationRule sharedOrderValidationRule: sharedOrderValidationRules) {
            BooleanResultMessage sharedValidationResultMessage =
                    sharedOrderValidationRule.checkRule(retailOrder);
            if (!sharedValidationResultMessage.getOperationResult()){
                resultMessages.add(sharedValidationResultMessage);
            }
        }

        return resultMessages;
    }

    public List<BooleanResultMessage> validateInstitutionalOrder(InstitutionalOrder institutionalOrder) {
        List<BooleanResultMessage> resultMessages= new ArrayList<>();

        for (IInstitutionalOrderValidationRule institutionalOrderValidationRule:
                institutionalOrderValidationRules) {
            BooleanResultMessage validationResultMessage =
                    institutionalOrderValidationRule.checkRule(institutionalOrder);
            if (!validationResultMessage.getOperationResult()){
                resultMessages.add(validationResultMessage);
            }
        }

        for (ISharedOrderValidationRule sharedOrderValidationRule: sharedOrderValidationRules) {
            BooleanResultMessage sharedValidationResultMessage =
                    sharedOrderValidationRule.checkRule(institutionalOrder);
            if (!sharedValidationResultMessage.getOperationResult()){
                resultMessages.add(sharedValidationResultMessage);
            }
        }

        return resultMessages;
    }
}
