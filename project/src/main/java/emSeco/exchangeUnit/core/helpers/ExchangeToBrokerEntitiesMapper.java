package emSeco.exchangeUnit.core.helpers;

import emSeco.brokerUnit.core.entities.shared.*;
import emSeco.brokerUnit.core.entities.trade.Trade;
import emSeco.exchangeUnit.core.entities.shared.InitiatorType;
import emSeco.exchangeUnit.core.entities.settlementResult.SettlementResult;

import java.util.ArrayList;
import java.util.List;

public class ExchangeToBrokerEntitiesMapper {
    public static List<Trade> mapExchangeTradeToBrokerTrade
            (List<emSeco.exchangeUnit.core.entities.trade.Trade> exchangeTrades) {

        List<emSeco.brokerUnit.core.entities.trade.Trade> brokerTrades = new ArrayList<>();

        for (emSeco.exchangeUnit.core.entities.trade.Trade exchangeTrade : exchangeTrades) {
            brokerTrades.add(
                    new emSeco.brokerUnit.core.entities.trade.Trade(
                            exchangeTrade.getExchangeId(), exchangeTrade.getTradeId(),
                            exchangeTrade.getTradeTimestamp(),
                            mapExchangeSideToBrokerSide(exchangeTrade.getBuySide()),
                            mapExchangeSideToBrokerSide(exchangeTrade.getSellSide())));
        }

        return brokerTrades;
    }

    public static List<emSeco.brokerUnit.core.entities.settlementResult.SettlementResult>
    mapExchangeSettlementResultToBrokerSettlementResult
            (List<SettlementResult> exchangeSettlementResults) {

        List<emSeco.brokerUnit.core.entities.settlementResult.SettlementResult> brokerSettlementResults = new ArrayList<>();
        for (SettlementResult exchangeSettlementResult : exchangeSettlementResults) {

            brokerSettlementResults.add(
                    new emSeco.brokerUnit.core.entities.settlementResult.SettlementResult(
                            new emSeco.brokerUnit.core.entities.trade.Trade(
                                    exchangeSettlementResult.getTrade().getExchangeId(),
                                    exchangeSettlementResult.getTrade().getTradeId(),
                                    exchangeSettlementResult.getTrade().getTradeTimestamp(),
                                    mapExchangeSideToBrokerSide(exchangeSettlementResult.getTrade().getBuySide()),
                                    mapExchangeSideToBrokerSide(exchangeSettlementResult.getTrade().getSellSide())
                            ), exchangeSettlementResult.getTradeSettlementTimestamp()
                    ));
        }

        return brokerSettlementResults;
    }

    private static Side mapExchangeSideToBrokerSide(emSeco.exchangeUnit.core.entities.shared.Side exchangeSide) {
        emSeco.brokerUnit.core.entities.shared.InitiatorType initiatorType;
        if (exchangeSide.getTradingInformation().getInitiatorType() == InitiatorType.institutional) {
            initiatorType = emSeco.brokerUnit.core.entities.shared.InitiatorType.institutional;
        } else {
            initiatorType = emSeco.brokerUnit.core.entities.shared.InitiatorType.retail;
        }

        return new Side(
                new RoutingInformation(
                        exchangeSide.getRoutingInformation().getBrokerId(),
                        exchangeSide.getRoutingInformation().getCustodianId()
                ),
                new AccountsInformation(
                        exchangeSide.getAccountsInformation().getClearingBankId(),
                        exchangeSide.getAccountsInformation().getClearingBankAccountNumber(),
                        exchangeSide.getAccountsInformation().getDepositoryId(),
                        exchangeSide.getAccountsInformation().getDematAccountNumber()
                ),
                new AllocationDetailInformation(
                        exchangeSide.getAllocationDetailInformation().getAllocationDetailBlockId(),
                        exchangeSide.getAllocationDetailInformation().getAllocationDetailId()
                ),
                new TradingInformation(
                        exchangeSide.getTradingInformation().getInitialOrderId(),
                        initiatorType,
                        exchangeSide.getTradingInformation().getClientTradingCode(),
                        exchangeSide.getTradingInformation().getRegisteredCode()
                ),
                new Term(
                        exchangeSide.getTerm().getPrice(),
                        exchangeSide.getTerm().getQuantity(),
                        exchangeSide.getTerm().getInstrumentName()
                ));
    }
}
