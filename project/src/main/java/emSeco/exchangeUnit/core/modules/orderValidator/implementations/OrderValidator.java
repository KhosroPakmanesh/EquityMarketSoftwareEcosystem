package emSeco.exchangeUnit.core.modules.orderValidator.implementations;

import com.google.inject.Inject;
import emSeco.exchangeUnit.core.entities.order.Order;
import emSeco.exchangeUnit.core.modules.orderValidationRules.institutionalOrderValidationRules.interfaces.IInstitutionalOrderValidationRule;
import emSeco.exchangeUnit.core.modules.orderValidationRules.retailOrderValidationRules.interfaces.IRetailOrderValidationRule;
import emSeco.exchangeUnit.core.modules.orderValidationRules.sharedOrderValidationRules.interfaces.ISharedOrderValidationRule;
import emSeco.exchangeUnit.core.modules.orderValidator.interfaces.IOrderValidator;
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

    public List<BooleanResultMessage> validateRetailOrder(Order retailOrder){
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

    public List<BooleanResultMessage> validateInstitutionalOrder(Order institutionalOrder) {
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
