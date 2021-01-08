package emSeco.custodianUnit.core.modules.allocationDetailValidationRules.implementations;

//#if CustodianAllocationDetailTermInformationValidation
import emSeco.custodianUnit.core.entities.allocationDetail.AllocationDetail;
import emSeco.custodianUnit.core.modules.allocationDetailValidationRules.interfaces.IAllocationDetailValidationRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;

import java.util.List;

public class AllocationDetailTermInformationValidation implements IAllocationDetailValidationRule {
    @Override
    public BooleanResultMessage checkRule(List<AllocationDetail> allocationDetails) {
        for (int counter = 0, allocationDetailsSize = allocationDetails.size();
             counter < allocationDetailsSize; counter++) {
            AllocationDetail allocationDetail = allocationDetails.get(counter);

            if (allocationDetail.getTerm().getInstrumentName().equals("")) {
                return new BooleanResultMessage
                        (false, OperationMessage.
                                Create("The instrumentName field for the allocation detail number "
                                        + counter + " is empty!"));
            }
            if (allocationDetail.getTerm().getPrice() <= 0) {
                return new BooleanResultMessage
                        (false, OperationMessage.
                                Create("The price field for the allocation detail number "
                                        + counter + " is empty!"));
            }
            if (allocationDetail.getTerm().getQuantity() <= 0) {
                return new BooleanResultMessage
                        (false, OperationMessage.
                                Create("The quantity field for the allocation detail number "
                                        + counter + " is empty!"));
            }
        }

        return new BooleanResultMessage
                (true, OperationMessage.emptyOperationMessage());
    }
}
//#endif