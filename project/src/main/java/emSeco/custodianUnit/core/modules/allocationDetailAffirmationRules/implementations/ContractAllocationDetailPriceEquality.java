package emSeco.custodianUnit.core.modules.allocationDetailAffirmationRules.implementations;

//#if ContractAllocationDetailPriceEquality
import emSeco.custodianUnit.core.entities.allocationDetail.AllocationDetail;
import emSeco.custodianUnit.core.entities.contract.Contract;
import emSeco.custodianUnit.core.modules.allocationDetailAffirmationRules.interfaces.IAllocationDetailAffirmationRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;

public class ContractAllocationDetailPriceEquality implements IAllocationDetailAffirmationRule {
    @Override
    public BooleanResultMessage checkRule(Contract contract, AllocationDetail allocationDetail) {
        if (allocationDetail.getTerm().getPrice() != contract.getBuySide().getTerm().getPrice()) {
            return new BooleanResultMessage(false,
                            OperationMessage.Create("The price of the contract with the client trading code "
                            + contract.getBuySide().getTradingInformation().getClientTradingCode() +
                            " is not equal with its peer allocation detail!"));
        }

        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }
}
//#endif