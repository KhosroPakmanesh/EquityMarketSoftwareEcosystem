package emSeco.clearingCropUnit.core.modules.tradeSettler.implementations;

import com.google.inject.Inject;
import emSeco.clearingCropUnit.core.entities.trade.InstitutionalTradeType;
import emSeco.clearingCropUnit.core.entities.trade.Trade;
import emSeco.clearingCropUnit.core.entities.settlementResult.SettlementResult;
import emSeco.clearingCropUnit.core.modules.moneyAndEquityTransferUnit.models.NovateMoneyAndSharesOutputClass;
import emSeco.clearingCropUnit.core.modules.moneyAndEquityTransferUnit.interfaces.IMoneyAndEquityTransferUnit;
import emSeco.clearingCropUnit.core.modules.tradeClearingRulesEvaluator.interfaces.ITradeClearingRulesEvaluator;
import emSeco.clearingCropUnit.core.modules.tradeSettler.interfaces.ITradeSettler;
import emSeco.clearingCropUnit.core.modules.tradeSettler.models.SettleTwoSideRetailTradesOutputClass;
import emSeco.clearingCropUnit.core.modules.tradeSettler.models.SettleOneSideInstitutionalTradesOutputClass;
import emSeco.clearingCropUnit.core.modules.tradeSettler.models.SettleTradesOutputClass;
import emSeco.clearingCropUnit.core.modules.tradeSettler.models.SettleTwoSideInstitutionalTradesOutputClass;
import emSeco.clearingCropUnit.core.modules.twoInstitutionalTradesMatcher.interfaces.ITwoInstitutionalTradesMatcher;
import emSeco.clearingCropUnit.core.services.infrastructureServices.databases.clearingCorpUnitRepositories.interfaces.IClearingCorpUnitRepositories;
import emSeco.clearingCropUnit.core.services.infrastructureServices.gateways.clearingCorpApiGateway.interfaces.IClearingCorUnitpApiGateways;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;


import java.util.List;
import java.util.stream.Collectors;

public class TradeSettler implements ITradeSettler {
    private final ITradeClearingRulesEvaluator tradeClearingRulesEvaluator;
    private final ITwoInstitutionalTradesMatcher twoInstitutionalTradesMatcher;
    private final IMoneyAndEquityTransferUnit moneyAndShareTransferUnit;
    private final IClearingCorUnitpApiGateways clearingCorpApiGateways;
    private final IClearingCorpUnitRepositories clearingCorpUnitRepositories;

    @Inject
    public TradeSettler(ITradeClearingRulesEvaluator tradeClearingRulesEvaluator,
                        ITwoInstitutionalTradesMatcher twoInstitutionalTradesMatcher,
                        IMoneyAndEquityTransferUnit moneyAndShareTransferUnit,
                        IClearingCorUnitpApiGateways clearingCorpApiGateways,
                        IClearingCorpUnitRepositories clearingCorpUnitRepositories) {
        this.tradeClearingRulesEvaluator = tradeClearingRulesEvaluator;
        this.twoInstitutionalTradesMatcher = twoInstitutionalTradesMatcher;
        this.moneyAndShareTransferUnit = moneyAndShareTransferUnit;
        this.clearingCorpApiGateways = clearingCorpApiGateways;
        this.clearingCorpUnitRepositories = clearingCorpUnitRepositories;
    }

    public SettleTradesOutputClass settleTrades(List<Trade> trades) {
        SettleTradesOutputClass settleTradesOutputClass = new SettleTradesOutputClass();

        List<Trade> twoSideInstitutionalTrades = trades.stream().filter(trade -> trade.getInstitutionalTradeType()
                == InstitutionalTradeType.twoSideInstitutional).collect(Collectors.toList());
        twoSideInstitutionalTrades.stream().
                collect(Collectors.groupingBy(Trade::getTradeId)).
                forEach((tradeId, groupedTrades) ->
                        settleTradesOutputClass.getSettleTwoSideInstitutionalTradesOutputClasses()
                                .add(settleTwoSideInstitutionalTrades(groupedTrades)));

        List<Trade> oneSideInstitutionalTrades = trades.stream().filter(trade -> trade.getInstitutionalTradeType()
                == InstitutionalTradeType.oneSideInstitutional).collect(Collectors.toList());
        oneSideInstitutionalTrades.stream().
                collect(Collectors.groupingBy(Trade::getTradeId)).
                forEach((tradeId, groupedTrades) ->
                        settleTradesOutputClass.getSettleOneSideInstitutionalTradesOutputClasses()
                                .add(settleOneSideInstitutionalTrades(groupedTrades)));

        List<Trade> twoSideRetailTrades = trades.stream().filter(trade -> trade.getInstitutionalTradeType()
                == InstitutionalTradeType.TwoSideRetail).collect(Collectors.toList());
        twoSideRetailTrades.stream().
                collect(Collectors.groupingBy(Trade::getTradeId)).
                forEach((tradeId, groupedTrades) ->
                        settleTradesOutputClass.getSettleTwoSideRetailTradesOutputClasses()
                                .add(settleTwoSideRetailTrades(groupedTrades)));

        return settleTradesOutputClass;
    }

    private SettleTwoSideInstitutionalTradesOutputClass settleTwoSideInstitutionalTrades(List<Trade> trades) {
        SettleTwoSideInstitutionalTradesOutputClass settleTwoSideInstitutionalTradesOutputClass =
                new SettleTwoSideInstitutionalTradesOutputClass();

        List<Trade> matchedTrades = twoInstitutionalTradesMatcher.MatchTwoTradeSides(trades);

        List<BooleanResultMessage> tradeEvaluationResultMessages =
                tradeClearingRulesEvaluator.EvaluateTwoSideInstitutionalTrades(matchedTrades);

        if (tradeEvaluationResultMessages.size() > 0) {
            settleTwoSideInstitutionalTradesOutputClass.
                    setTradeEvaluationResultMessages(tradeEvaluationResultMessages);

            return settleTwoSideInstitutionalTradesOutputClass;
        }

        NovateMoneyAndSharesOutputClass novateMoneyAndSharesOutputClass =
                moneyAndShareTransferUnit.NovateMoneyAndShares(matchedTrades);

        if (!novateMoneyAndSharesOutputClass.getTransferResultMessage().getOperationResult()) {
            settleTwoSideInstitutionalTradesOutputClass.setTransferResultMessage
                    (novateMoneyAndSharesOutputClass.getTransferResultMessage());

            return settleTwoSideInstitutionalTradesOutputClass;
        }

        List<SettlementResult> settlementResults = novateMoneyAndSharesOutputClass.getSettlementResults();
        settleTwoSideInstitutionalTradesOutputClass.setSettlementResults(settlementResults);

        clearingCorpApiGateways.getClearingCorpToCustodianUnitApiGateway().
                submitSettlementResults(settlementResults);
        clearingCorpUnitRepositories.getSettlementResultRepository().add(settlementResults);

        return settleTwoSideInstitutionalTradesOutputClass;
    }

    private SettleOneSideInstitutionalTradesOutputClass settleOneSideInstitutionalTrades(List<Trade> trades) {
        SettleOneSideInstitutionalTradesOutputClass settleOneSideInstitutionalTradesOutputClass =
                new SettleOneSideInstitutionalTradesOutputClass();

        List<BooleanResultMessage> tradeEvaluationResultMessages =
                tradeClearingRulesEvaluator.EvaluateOneSideInstitutionalTrades(trades);

        if (tradeEvaluationResultMessages.size() > 0) {
            settleOneSideInstitutionalTradesOutputClass.
                    setTradeEvaluationResultMessages(tradeEvaluationResultMessages);

            return settleOneSideInstitutionalTradesOutputClass;
        }

        NovateMoneyAndSharesOutputClass novateMoneyAndSharesOutputClass =
                moneyAndShareTransferUnit.NovateMoneyAndShares(trades);

        if (!novateMoneyAndSharesOutputClass.getTransferResultMessage().getOperationResult()) {
            settleOneSideInstitutionalTradesOutputClass.setTransferResultMessage
                    (novateMoneyAndSharesOutputClass.getTransferResultMessage());

            return settleOneSideInstitutionalTradesOutputClass;
        }

        List<SettlementResult> settlementResults = novateMoneyAndSharesOutputClass.getSettlementResults();
        settleOneSideInstitutionalTradesOutputClass.setSettlementResults(settlementResults);

        clearingCorpApiGateways.getClearingCorpToCustodianUnitApiGateway().
                submitSettlementResults(settlementResults);
        clearingCorpApiGateways.getClearingCorpToExchangeUnitApiGateway().
                submitSettlementResults(settlementResults);
        clearingCorpUnitRepositories.getSettlementResultRepository().add(settlementResults);

        return settleOneSideInstitutionalTradesOutputClass;
    }

    private SettleTwoSideRetailTradesOutputClass settleTwoSideRetailTrades(List<Trade> trades) {
        SettleTwoSideRetailTradesOutputClass settleTwoSideRetailTradesOutputClass =
                new SettleTwoSideRetailTradesOutputClass();

        List<BooleanResultMessage> tradeEvaluationResultMessages =
                tradeClearingRulesEvaluator.EvaluateTwoSideRetailTrades(trades);

        if (tradeEvaluationResultMessages.size() > 0) {
            settleTwoSideRetailTradesOutputClass.
                    setTradeEvaluationResultMessages(tradeEvaluationResultMessages);

            return settleTwoSideRetailTradesOutputClass;
        }

        NovateMoneyAndSharesOutputClass novateMoneyAndSharesOutputClass =
                moneyAndShareTransferUnit.NovateMoneyAndShares(trades);

        if (!novateMoneyAndSharesOutputClass.getTransferResultMessage().getOperationResult()) {
            settleTwoSideRetailTradesOutputClass.setTransferResultMessage
                    (novateMoneyAndSharesOutputClass.getTransferResultMessage());

            return settleTwoSideRetailTradesOutputClass;
        }

        List<SettlementResult> settlementResults = novateMoneyAndSharesOutputClass.getSettlementResults();
        settleTwoSideRetailTradesOutputClass.setSettlementResults(settlementResults);

        clearingCorpApiGateways.getClearingCorpToExchangeUnitApiGateway().
                submitSettlementResults(settlementResults);
        clearingCorpUnitRepositories.getSettlementResultRepository()
                .add(settlementResults);

        return settleTwoSideRetailTradesOutputClass;
    }
}