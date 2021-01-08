package emSeco.brokerUnit.core.modules.allocationDetailValidator.implementations;

import com.google.inject.Inject;
import emSeco.brokerUnit.core.entities.allocationDetail.AllocationDetail;
import emSeco.brokerUnit.core.modules.allocationDetailValidationRules.interfaces.IAllocationDetailValidationRule;
import emSeco.brokerUnit.core.modules.allocationDetailValidator.interfaces.IAllocationDetailValidator;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AllocationDetailValidator implements IAllocationDetailValidator {
    private final Set<IAllocationDetailValidationRule> allocationDetailValidationRules;

    @Inject
    public AllocationDetailValidator(Set<IAllocationDetailValidationRule> allocationDetailValidationRules ) {
        this.allocationDetailValidationRules = allocationDetailValidationRules;
    }

    @Override
    public List<BooleanResultMessage> validateAllocationDetails(List<AllocationDetail> allocationDetails) {
        List<BooleanResultMessage> resultMessages= new ArrayList<>();

        for (IAllocationDetailValidationRule allocationDetailValidationRule:
                allocationDetailValidationRules) {
            BooleanResultMessage allocationDetailValidationResultMessages =
                    allocationDetailValidationRule.checkRule(allocationDetails);
            if (!allocationDetailValidationResultMessages.getOperationResult())
            {
                resultMessages.add(allocationDetailValidationResultMessages);
            }
        }

        return resultMessages;
    }
}