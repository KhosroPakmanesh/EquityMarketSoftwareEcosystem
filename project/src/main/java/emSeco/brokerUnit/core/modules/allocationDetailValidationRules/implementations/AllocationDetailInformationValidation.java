package emSeco.brokerUnit.core.modules.allocationDetailValidationRules.implementations;

//#if BrokerAllocationDetailInformationValidation
import emSeco.brokerUnit.core.entities.allocationDetail.AllocationDetail;
import emSeco.brokerUnit.core.modules.allocationDetailValidationRules.interfaces.IAllocationDetailValidationRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;


import java.util.List;

public class AllocationDetailInformationValidation implements IAllocationDetailValidationRule {
    @Override
    public BooleanResultMessage checkRule(List<AllocationDetail> allocationDetails) {
        for (int counter = 0, allocationDetailsSize = allocationDetails.size();
             counter < allocationDetailsSize; counter++) {
            AllocationDetail allocationDetail = allocationDetails.get(counter);

            if (allocationDetail.getAllocationDetailInformation().getAllocationDetailBlockId() == null) {
                return new BooleanResultMessage
                        (false, OperationMessage.
                                Create("The allocationDetailBlockId field for the allocation detail number "
                                        + counter + " is empty!"));
            }
            if (allocationDetail.getAllocationDetailInformation().getAllocationDetailId() == null) {
                return new BooleanResultMessage
                        (false, OperationMessage.
                                Create("The allocationDetailId field for the allocation detail number "
                                        + counter + " is empty!"));
            }
        }

        return new BooleanResultMessage
                (true, OperationMessage.emptyOperationMessage());
    }
}
//#endif
