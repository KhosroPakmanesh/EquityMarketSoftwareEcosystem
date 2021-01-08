package emSeco.exchangeUnit.core.helpers;

import emSeco.clearingCropUnit.core.entities.shared.*;
import emSeco.clearingCropUnit.core.entities.trade.Trade;
import emSeco.exchangeUnit.core.entities.shared.InitiatorType;

import java.util.ArrayList;
import java.util.List;

public class ExchangeToClearingCorpEntitiesMapper {
    public static List<Trade> mapFromExchangeTradeToClearingCorpTrade
            (List<emSeco.exchangeUnit.core.entities.trade.Trade> exchangeTrades) {
        List<Trade> clearingCorpTrades = new ArrayList<>();

        for (emSeco.exchangeUnit.core.entities.trade.Trade trade : exchangeTrades) {
            emSeco.clearingCropUnit.core.entities.shared.InitiatorType buyInitiatorType;
            emSeco.clearingCropUnit.core.entities.shared.InitiatorType sellInitiatorType;
            if (trade.getBuySide().getTradingInformation().getInitiatorType() == InitiatorType.institutional) {
                buyInitiatorType = emSeco.clearingCropUnit.core.entities.shared.InitiatorType.institutional;
            } else {
                buyInitiatorType = emSeco.clearingCropUnit.core.entities.shared.InitiatorType.retail;
            }
            if (trade.getSellSide().getTradingInformation().getInitiatorType() == InitiatorType.institutional) {
                sellInitiatorType = emSeco.clearingCropUnit.core.entities.shared.InitiatorType.institutional;
            } else {
                sellInitiatorType = emSeco.clearingCropUnit.core.entities.shared.InitiatorType.retail;
            }

            clearingCorpTrades.add(
                    new emSeco.clearingCropUnit.core.entities.trade.Trade(
                    trade.getExchangeId(), trade.getTradeId(), trade.getTradeTimestamp(),
                    new Side(
                            new RoutingInformation(
                                    trade.getBuySide().getRoutingInformation().getBrokerId(),
                                    trade.getBuySide().getRoutingInformation().getCustodianId()
                            ),
                            new AccountsInformation(
                                    trade.getBuySide().getAccountsInformation().getClearingBankId(),
                                    trade.getBuySide().getAccountsInformation().getClearingBankAccountNumber(),
                                    trade.getBuySide().getAccountsInformation().getDepositoryId(),
                                    trade.getBuySide().getAccountsInformation().getDematAccountNumber()

                            ),
                            new AllocationDetailInformation(
                                    null,
                                    null
                            ),
                            new TradingInformation(
                                    trade.getBuySide().getTradingInformation().getInitialOrderId(),
                                    buyInitiatorType,
                                    trade.getBuySide().getTradingInformation().getClientTradingCode(),
                                    trade.getBuySide().getTradingInformation().getRegisteredCode()
                            ),
                            new Term(
                                    trade.getBuySide().getTerm().getPrice(),
                                    trade.getBuySide().getTerm().getQuantity(),
                                    trade.getBuySide().getTerm().getInstrumentName()
                            )),
                    new Side(
                            new RoutingInformation(
                                    trade.getSellSide().getRoutingInformation().getBrokerId(),
                                    trade.getSellSide().getRoutingInformation().getCustodianId()
                            ),
                            new AccountsInformation(

                                    trade.getSellSide().getAccountsInformation().getClearingBankId(),
                                    trade.getSellSide().getAccountsInformation().getClearingBankAccountNumber(),
                                    trade.getSellSide().getAccountsInformation().getDepositoryId(),
                                    trade.getSellSide().getAccountsInformation().getDematAccountNumber()
                            ),
                            new AllocationDetailInformation(
                                    null,
                                    null
                            ),
                            new TradingInformation(
                                    trade.getSellSide().getTradingInformation().getInitialOrderId(),
                                    sellInitiatorType,
                                    trade.getSellSide().getTradingInformation().getClientTradingCode(),
                                    trade.getSellSide().getTradingInformation().getRegisteredCode()
                            ),
                            new Term(
                                    trade.getSellSide().getTerm().getPrice(),
                                    trade.getSellSide().getTerm().getQuantity(),
                                    trade.getSellSide().getTerm().getInstrumentName()
                            )))
            );
        }

        return clearingCorpTrades;
    }
}
