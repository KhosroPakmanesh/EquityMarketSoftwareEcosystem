package emSeco.custodianUnit.core.modules.allocationDetailValidationRules.implementations;

//#if CustodianAllocationDetailsAgreement
import emSeco.custodianUnit.core.entities.allocationDetail.AllocationDetail;
import emSeco.custodianUnit.core.modules.allocationDetailValidationRules.interfaces.IAllocationDetailValidationRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;


import java.util.List;
import java.util.stream.Collectors;

public class AllocationDetailsAgreement implements IAllocationDetailValidationRule {
    @Override
    public BooleanResultMessage checkRule(List<AllocationDetail> allocationDetails) {

        int differentInitialOrdersNumber = allocationDetails.stream().collect(Collectors.groupingBy
                (allocationDetail -> allocationDetail.getTradingInformation().getInitialOrderId())).size();

        if (differentInitialOrdersNumber > 1)
        {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("Allocation detail's initial orders do not match!"));
        }

        int differentAllocationDetailBlockIdsNumber = allocationDetails.stream().collect(Collectors.groupingBy
                (allocationDetail -> allocationDetail.getAllocationDetailInformation().
                        getAllocationDetailBlockId())).size();

        if (differentAllocationDetailBlockIdsNumber > 1)
        {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("Allocation detail's allocation detail block Ids do not match!"));
        }

        int differentPricesNumber = allocationDetails.stream().collect(Collectors.groupingBy
                (allocationDetail -> allocationDetail.getTerm().getPrice())).size();

        if (differentPricesNumber > 1)
        {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("Allocation detail's prices do not match!"));
        }

        int differentInstrumentsNumber = allocationDetails.stream().collect(Collectors.groupingBy
                (allocationDetail -> allocationDetail.getTerm().getInstrumentName())).size();

        if (differentInstrumentsNumber > 1)
        {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("Allocation detail's instrument names do not match!"));
        }

        return new BooleanResultMessage
                (true, OperationMessage.emptyOperationMessage());
    }
}
//#endif