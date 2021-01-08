package emSeco.clearingCropUnit.core.modules.tradeValidationRules.twoSideInstitutionalTradeValidationRules.implementations;

//#if TwoSideInstitutionalTradeAccountsInformationValidation
import emSeco.clearingCropUnit.core.entities.trade.Trade;
import emSeco.clearingCropUnit.core.modules.tradeValidationRules.twoSideInstitutionalTradeValidationRules.interfaces.ITwoSideInstitutionalTradeValidationRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;


//OK.
public class TwoSideInstitutionalTradeAccountsInformationValidation implements ITwoSideInstitutionalTradeValidationRule {
    @Override
    public BooleanResultMessage checkRule(Trade trade) {
        if (trade.getTradeOwnerSide().getAccountsInformation().getClearingBankId() == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The clearingBankId field of the owner side is empty!"));
        }

        if (trade.getTradeOwnerSide().getAccountsInformation().getClearingBankAccountNumber() == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The clearing bank account number of the owner side is empty!"));
        }

        if (trade.getTradeOwnerSide().getAccountsInformation().getDepositoryId() == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The depositoryId field of the owner side is empty!"));
        }

        if (trade.getTradeOwnerSide().getAccountsInformation().getDematAccountNumber() == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The demat account number field of the owner side is empty!"));
        }

        return new BooleanResultMessage
                (true, OperationMessage.emptyOperationMessage());
    }
}
//#endif