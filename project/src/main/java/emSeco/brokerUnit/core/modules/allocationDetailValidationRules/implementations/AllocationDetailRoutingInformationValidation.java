package emSeco.brokerUnit.core.modules.allocationDetailValidationRules.implementations;

//#if BrokerAllocationDetailRoutingInformationValidation
import emSeco.brokerUnit.core.entities.allocationDetail.AllocationDetail;
import emSeco.brokerUnit.core.modules.allocationDetailValidationRules.interfaces.IAllocationDetailValidationRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;


import java.util.List;

public class AllocationDetailRoutingInformationValidation implements IAllocationDetailValidationRule {
    @Override
    public BooleanResultMessage checkRule(List<AllocationDetail> allocationDetails) {
        for (int counter = 0, allocationDetailsSize = allocationDetails.size();
             counter < allocationDetailsSize; counter++) {
            AllocationDetail allocationDetail = allocationDetails.get(counter);

            if (allocationDetail.getRoutingInformation().getCustodianId() == null) {
                return new BooleanResultMessage
                        (false, OperationMessage.
                                Create("The custodianId field for the allocation detail number "
                                        + counter + " is empty!"));
            }
        }

        return new BooleanResultMessage
                (true, OperationMessage.emptyOperationMessage());
    }
}
//#endif
