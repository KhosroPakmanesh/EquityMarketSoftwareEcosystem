package emSeco.clearingCropUnit.core.modules.tradeValidationRules.twoSideInstitutionalTradeValidationRules.implementations;

//#if TwoSideInstitutionalAllocationDetailInformationValidation
import emSeco.clearingCropUnit.core.entities.trade.Trade;
import emSeco.clearingCropUnit.core.modules.tradeValidationRules.twoSideInstitutionalTradeValidationRules.interfaces.ITwoSideInstitutionalTradeValidationRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;

public class TwoSideInstitutionalAllocationDetailInformationValidation implements ITwoSideInstitutionalTradeValidationRule {
    @Override
    public BooleanResultMessage checkRule(Trade trade) {
        if (trade.getTradeOwnerSide().getAllocationDetailInformation().getAllocationDetailBlockId() == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The allocationDetailBlockId field of the owner side is empty!"));
        }

        if (trade.getTradeOwnerSide().getAllocationDetailInformation().getAllocationDetailId() == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The allocationDetailId field of the owner side is empty!"));
        }

        return new BooleanResultMessage
                (true, OperationMessage.emptyOperationMessage());
    }
}
//#endif