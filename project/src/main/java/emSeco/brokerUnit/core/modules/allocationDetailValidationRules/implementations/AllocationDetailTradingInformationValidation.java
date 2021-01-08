package emSeco.brokerUnit.core.modules.allocationDetailValidationRules.implementations;

//#if BrokerAllocationDetailTradingInformationValidation
import emSeco.brokerUnit.core.entities.allocationDetail.AllocationDetail;
import emSeco.brokerUnit.core.modules.allocationDetailValidationRules.interfaces.IAllocationDetailValidationRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;


import java.util.List;

public class AllocationDetailTradingInformationValidation implements IAllocationDetailValidationRule {
    @Override
    public BooleanResultMessage checkRule(List<AllocationDetail> allocationDetails) {
        for (int counter = 0, allocationDetailsSize = allocationDetails.size();
             counter < allocationDetailsSize; counter++) {
            AllocationDetail allocationDetail = allocationDetails.get(counter);

            if (allocationDetail.getTradingInformation().getRegisteredCode() == null) {
                return new BooleanResultMessage
                        (false, OperationMessage.
                                Create("The registeredCode field for the allocation detail number "
                                        + counter + " is empty!"));
            }

            if (allocationDetail.getTradingInformation().getInitialOrderId() == null) {
                return new BooleanResultMessage
                        (false, OperationMessage.
                                Create("The initialOrderId field for the allocation detail number "
                                        + counter + " is empty!"));
            }
            if (allocationDetail.getTradingInformation().getClientTradingCode() == null) {
                return new BooleanResultMessage
                        (false, OperationMessage.
                                Create("The client Trading Code field for the allocation detail number "
                                        + counter + " is empty!"));
            }
            if (allocationDetail.getTradingInformation().getInitiatorType() == null) {
                return new BooleanResultMessage
                        (false, OperationMessage.
                                Create("The orderInitiatorType field for the allocation detail number "
                                        + counter + " is empty!"));
            }
        }

        return new BooleanResultMessage
                (true, OperationMessage.emptyOperationMessage());
    }
}
//#endif
