package emSeco.custodianUnit.core.modules.allocationDetailAffirmationRules.implementations;

//#if ContractAllocationDetailInstrumentEquality
import emSeco.custodianUnit.core.entities.allocationDetail.AllocationDetail;
import emSeco.custodianUnit.core.entities.contract.Contract;
import emSeco.custodianUnit.core.modules.allocationDetailAffirmationRules.interfaces.IAllocationDetailAffirmationRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;

public class ContractAllocationDetailInstrumentEquality implements IAllocationDetailAffirmationRule {
    @Override
    public BooleanResultMessage checkRule(Contract contract, AllocationDetail allocationDetail) {
        if (allocationDetail.getTerm().getInstrumentName().equals(contract.getBuySide().getTerm().getInstrumentName())) {
            return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
        }

        return new BooleanResultMessage(false,
                OperationMessage.Create
                        ("The instrument name of the contract with the client trading code "
                                + contract.getBuySide().getTradingInformation().getClientTradingCode() +
                                " is not equal with its allocation detail!"));
    }
}
//#endif