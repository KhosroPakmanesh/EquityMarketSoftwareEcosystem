package emSeco.custodianUnit.core.modules.allocationDetailAffirmationRules.implementations;

//#if ContractAllocationDetailQuantityEquality
import emSeco.custodianUnit.core.entities.allocationDetail.AllocationDetail;
import emSeco.custodianUnit.core.entities.contract.Contract;
import emSeco.custodianUnit.core.modules.allocationDetailAffirmationRules.interfaces.IAllocationDetailAffirmationRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;


public class ContractAllocationDetailQuantityEquality implements IAllocationDetailAffirmationRule {
    @Override
    public BooleanResultMessage checkRule(Contract contract, AllocationDetail allocationDetail) {
        if (allocationDetail.getTerm().getQuantity() != contract.getBuySide().getTerm().getQuantity()) {
            return new BooleanResultMessage(false,
                    OperationMessage.Create("The quantity of the contract with the client trading code "
                            + contract.getBuySide().getTradingInformation().getClientTradingCode() +
                            " is not equal with its peer allocation detail!"));
        }

        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }
}
//#endif