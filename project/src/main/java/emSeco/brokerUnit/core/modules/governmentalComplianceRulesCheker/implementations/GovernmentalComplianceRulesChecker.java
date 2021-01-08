package emSeco.brokerUnit.core.modules.governmentalComplianceRulesCheker.implementations;

import com.google.inject.Inject;
import emSeco.brokerUnit.core.entities.order.InstitutionalOrder;
import emSeco.brokerUnit.core.entities.order.RetailOrder;
import emSeco.brokerUnit.core.modules.governmentalComplianceRules.sharedlGovernmentalComplianceRules.interfaces.ISharedGovernmentalComplianceRule;
import emSeco.brokerUnit.core.modules.governmentalComplianceRules.institutionalGovernmentalComplianceRules.interfaces.IInstitutionalGovernmentalComplianceRule;
import emSeco.brokerUnit.core.modules.governmentalComplianceRules.retailGovernmentalComplianceRules.interfaces.IRetailGovernmentalComplianceRule;
import emSeco.brokerUnit.core.modules.governmentalComplianceRulesCheker.interfaces.IGovernmentalComplianceRulesChecker;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GovernmentalComplianceRulesChecker implements IGovernmentalComplianceRulesChecker {
    private final Set<IRetailGovernmentalComplianceRule> retailGovernmentalComplianceRules;
    private final Set<IInstitutionalGovernmentalComplianceRule> institutionalGovernmentalComplianceRules;
    private final Set<ISharedGovernmentalComplianceRule> sharedGovernmentalComplianceRules;

    @Inject
    public GovernmentalComplianceRulesChecker
            (Set<IRetailGovernmentalComplianceRule> retailGovernmentalComplianceRules,
             Set<IInstitutionalGovernmentalComplianceRule> institutionalGovernmentalComplianceRules,
             Set<ISharedGovernmentalComplianceRule> sharedGovernmentalComplianceRules) {
        this.retailGovernmentalComplianceRules = retailGovernmentalComplianceRules;
        this.institutionalGovernmentalComplianceRules = institutionalGovernmentalComplianceRules;
        this.sharedGovernmentalComplianceRules = sharedGovernmentalComplianceRules;
    }

    @Override
    public List<BooleanResultMessage> checkRetailGovernmentalComplianceRules(RetailOrder retailOrder) {
        List<BooleanResultMessage> resultMessages= new ArrayList<>();

        for (IRetailGovernmentalComplianceRule retailGovernmentalComplianceRule:
                retailGovernmentalComplianceRules){
            BooleanResultMessage governmentalComplianceRuleValidationResultMessage =
                    retailGovernmentalComplianceRule.checkRule(retailOrder);
            if (!governmentalComplianceRuleValidationResultMessage.getOperationResult()){
                resultMessages.add(governmentalComplianceRuleValidationResultMessage);
            }
        }

        for (ISharedGovernmentalComplianceRule sharedGovernmentalComplianceRule:
                sharedGovernmentalComplianceRules){
            BooleanResultMessage sharedGovernmentalComplianceRuleValidationResultMessage =
                    sharedGovernmentalComplianceRule.checkRule(retailOrder);
            if (!sharedGovernmentalComplianceRuleValidationResultMessage.getOperationResult()){
                resultMessages.add(sharedGovernmentalComplianceRuleValidationResultMessage);
            }
        }

        return resultMessages;
    }

    @Override
    public List<BooleanResultMessage> checkInstitutionalGovernmentalComplianceRules(InstitutionalOrder institutionalOrder) {
        List<BooleanResultMessage> resultMessages= new ArrayList<>();

        for (IInstitutionalGovernmentalComplianceRule institutionalGovernmentalComplianceRule:
                institutionalGovernmentalComplianceRules){
            BooleanResultMessage governmentalComplianceRuleValidationResultMessage =
                    institutionalGovernmentalComplianceRule.checkRule(institutionalOrder);
            if (!governmentalComplianceRuleValidationResultMessage.getOperationResult()){
                resultMessages.add(governmentalComplianceRuleValidationResultMessage);
            }
        }

        for (ISharedGovernmentalComplianceRule sharedGovernmentalComplianceRule:
                sharedGovernmentalComplianceRules){
            BooleanResultMessage sharedGovernmentalComplianceRuleValidationResultMessage =
                    sharedGovernmentalComplianceRule.checkRule(institutionalOrder);
            if (!sharedGovernmentalComplianceRuleValidationResultMessage.getOperationResult()){
                resultMessages.add(sharedGovernmentalComplianceRuleValidationResultMessage);
            }
        }

        return resultMessages;
    }
}
