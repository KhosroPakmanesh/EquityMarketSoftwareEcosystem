package emSeco.custodianUnit.core.modules.allocationDetailAffirmationRules.interfaces;


import emSeco.custodianUnit.core.entities.allocationDetail.AllocationDetail;
import emSeco.custodianUnit.core.entities.contract.Contract;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;


public interface IAllocationDetailAffirmationRule {
    BooleanResultMessage checkRule(Contract contract, AllocationDetail allocationDetail);
}
