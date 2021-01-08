package emSeco.clearingCropUnit.core.modules.tradeClearingRules.sharedTradeClearingRules.implementations;

//#if SharedExchangeInformationEquality
import emSeco.clearingCropUnit.core.entities.trade.Trade;
import emSeco.clearingCropUnit.core.modules.tradeClearingRules.sharedTradeClearingRules.interfaces.ISharedTradeClearingRule;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class SharedExchangeInformationEquality implements ISharedTradeClearingRule {
    @Override
    public BooleanResultMessage checkRule(List<Trade> trades) {
        List<UUID> buySideExchangeIds = trades.stream().
                map(Trade::getExchangeId).
                distinct().collect(Collectors.toList());

        List<UUID> sellSideExchangeIds = trades.stream().
                map(Trade::getExchangeId).
                distinct().collect(Collectors.toList());

        if (!(buySideExchangeIds.size() == 1 && sellSideExchangeIds.size() == 1 &&
                buySideExchangeIds.stream().findAny().equals(sellSideExchangeIds.stream().findAny()))) {
            return new BooleanResultMessage(false
                    , OperationMessage.Create("The exchangeIds of both sides of the trades are not equal!"));
        }

        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }
}
//#endif
