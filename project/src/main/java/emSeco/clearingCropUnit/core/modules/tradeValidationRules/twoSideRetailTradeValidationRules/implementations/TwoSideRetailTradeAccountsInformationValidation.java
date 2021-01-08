package emSeco.clearingCropUnit.core.modules.tradeValidationRules.twoSideRetailTradeValidationRules.implementations;

//#if TwoSideRetailTradeAccountsInformationValidation
import emSeco.clearingCropUnit.core.entities.trade.Trade;
import emSeco.clearingCropUnit.core.modules.tradeValidationRules.twoSideRetailTradeValidationRules.interfaces.ITwoSideRetailTradeValidationRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;



public class TwoSideRetailTradeAccountsInformationValidation implements ITwoSideRetailTradeValidationRule {
    @Override
    public BooleanResultMessage checkRule(Trade trade) {
        if (trade.getBuySide().getAccountsInformation().getClearingBankId() == null ||
                trade.getSellSide().getAccountsInformation().getClearingBankId() == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The clearingBankId field of one or two sides are empty!"));
        }

        if (trade.getBuySide().getAccountsInformation().getClearingBankAccountNumber() == null ||
                trade.getSellSide().getAccountsInformation().getClearingBankAccountNumber() == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The clearing bank account number field of one or two sides are empty!"));
        }

        if (trade.getBuySide().getAccountsInformation().getDepositoryId() == null ||
                trade.getSellSide().getAccountsInformation().getDepositoryId() == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The depositoryId field of one or two sides are empty!"));
        }

        if (trade.getBuySide().getAccountsInformation().getDematAccountNumber() == null ||
                trade.getSellSide().getAccountsInformation().getDematAccountNumber() == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("The demat account number field of one or two sides are empty!"));
        }

        return new BooleanResultMessage
                (true, OperationMessage.emptyOperationMessage());
    }
}
//#endif