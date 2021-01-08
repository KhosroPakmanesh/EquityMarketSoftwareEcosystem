package emSeco.clearingCropUnit.core.modules.tradeClearingRules.twoSideRetailTradeClearingRules.implementations;

//#if FakeTwoSideRetailTradeClearingRuleNum1
import emSeco.clearingCropUnit.core.entities.trade.Trade;
import emSeco.clearingCropUnit.core.modules.tradeClearingRules.twoSideRetailTradeClearingRules.interfaces.ITwoSideRetailTradeClearingRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;


import java.util.List;

public class FakeTwoSideRetailTradeClearingRuleNum1 implements ITwoSideRetailTradeClearingRule {
    @Override
    public BooleanResultMessage checkRule(List<Trade> trades) {
        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }
}
//#endif
