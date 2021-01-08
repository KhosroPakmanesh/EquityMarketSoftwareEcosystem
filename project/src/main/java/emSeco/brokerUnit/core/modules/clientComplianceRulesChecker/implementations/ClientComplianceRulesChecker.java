package emSeco.brokerUnit.core.modules.clientComplianceRulesChecker.implementations;

import com.google.inject.Inject;
import emSeco.brokerUnit.core.entities.order.InstitutionalOrder;
import emSeco.brokerUnit.core.entities.order.RetailOrder;
import emSeco.brokerUnit.core.modules.clientComplianceRules.sharedClientComplianceRules.interfaces.ISharedClientComplianceRule;
import emSeco.brokerUnit.core.modules.clientComplianceRules.institutionalClientComplianceRules.interfaces.IInstitutionalClientComplianceRule;
import emSeco.brokerUnit.core.modules.clientComplianceRules.retailClientComplianceRules.interfaces.IRetailClientComplianceRule;
import emSeco.brokerUnit.core.modules.clientComplianceRulesChecker.interfaces.IClientComplianceRulesChecker;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ClientComplianceRulesChecker implements IClientComplianceRulesChecker {
    private final Set<IRetailClientComplianceRule> retailClientComplianceRules;
    private final Set<IInstitutionalClientComplianceRule> institutionalClientComplianceRules;
    private final Set<ISharedClientComplianceRule> sharedClientComplianceRules;

    @Inject
    public ClientComplianceRulesChecker(Set<IRetailClientComplianceRule> retailClientComplianceRules,
                                        Set<IInstitutionalClientComplianceRule> institutionalClientComplianceRules,
                                        Set<ISharedClientComplianceRule> sharedClientComplianceRules) {
        this.retailClientComplianceRules = retailClientComplianceRules;
        this.institutionalClientComplianceRules = institutionalClientComplianceRules;
        this.sharedClientComplianceRules = sharedClientComplianceRules;
    }

    @Override
    public List<BooleanResultMessage> checkRetailClientComplianceRules(RetailOrder retailOrder) {
        List<BooleanResultMessage> resultMessages= new ArrayList<>();

        for (IRetailClientComplianceRule retailClientComplianceRule: retailClientComplianceRules){
            BooleanResultMessage clientComplianceRuleValidationResultMessage=
                    retailClientComplianceRule.checkRule(retailOrder);
            if (!clientComplianceRuleValidationResultMessage.getOperationResult()){
                resultMessages.add(clientComplianceRuleValidationResultMessage);
            }
        }

        for (ISharedClientComplianceRule sharedClientComplianceRule: sharedClientComplianceRules){
            BooleanResultMessage sharedClientComplianceRuleValidationResultMessage=
                    sharedClientComplianceRule.checkRule(retailOrder);
            if (!sharedClientComplianceRuleValidationResultMessage.getOperationResult()){
                resultMessages.add(sharedClientComplianceRuleValidationResultMessage);
            }
        }

        return resultMessages;
    }

    @Override
    public List<BooleanResultMessage> checkInstitutionalClientComplianceRules(InstitutionalOrder institutionalOrder) {
        List<BooleanResultMessage> resultMessages= new ArrayList<>();

        for (IInstitutionalClientComplianceRule institutionalClientComplianceRule:
                institutionalClientComplianceRules){
            BooleanResultMessage clientComplianceRuleValidationResultMessage=
                    institutionalClientComplianceRule.checkRule(institutionalOrder);
            if (!clientComplianceRuleValidationResultMessage.getOperationResult()){
                resultMessages.add(clientComplianceRuleValidationResultMessage);
            }
        }

        for (ISharedClientComplianceRule sharedClientComplianceRule: sharedClientComplianceRules){
            BooleanResultMessage sharedClientComplianceRuleValidationResultMessage=
                    sharedClientComplianceRule.checkRule(institutionalOrder);
            if (!sharedClientComplianceRuleValidationResultMessage.getOperationResult()){
                resultMessages.add(sharedClientComplianceRuleValidationResultMessage);
            }
        }

        return resultMessages;
    }
}
