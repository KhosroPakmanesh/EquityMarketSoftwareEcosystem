package emSeco.clearingCropUnit.core.helpers;


import emSeco.custodianUnit.core.entities.shared.*;
import emSeco.custodianUnit.core.entities.trade.TradeSide;
import emSeco.exchangeUnit.core.entities.trade.Trade;
import emSeco.exchangeUnit.core.entities.settlementResult.SettlementResult;

import java.util.ArrayList;
import java.util.List;

public class ClearingCorpToCustodianEntitiesMapper {

    public static List<emSeco.custodianUnit.core.entities.settlementResult.SettlementResult>
    mapFromClearingCorpToCustodianSettlementResults
            (List<emSeco.clearingCropUnit.core.entities.settlementResult.SettlementResult> clearingCorpSettlementResults) {
        List<emSeco.custodianUnit.core.entities.settlementResult.SettlementResult> custodianSettlementResults = new ArrayList<>();

        for (emSeco.clearingCropUnit.core.entities.settlementResult.SettlementResult
                clearingCorpSettlementResult : clearingCorpSettlementResults) {

            custodianSettlementResults.add(
                    new emSeco.custodianUnit.core.entities.settlementResult.SettlementResult(
                            new emSeco.custodianUnit.core.entities.trade.Trade(
                                    clearingCorpSettlementResult.getTrade().getExchangeId(),
                                    clearingCorpSettlementResult.getTrade().getTradeId(),
                                    clearingCorpSettlementResult.getTrade().getTradeTimestamp(),
                                    mapClearingCorpSideToCustodianTradeSide(
                                            clearingCorpSettlementResult.getTrade().getBuySide()),
                                    mapClearingCorpSideToCustodianTradeSide(
                                            clearingCorpSettlementResult.getTrade().getSellSide())
                            ), clearingCorpSettlementResult.getTradeSettlementTimestamp()
                    ));
        }

        return custodianSettlementResults;
    }

    public static List<SettlementResult> mapFromClearingCorpToExchangeSettlementResults
            (List<emSeco.clearingCropUnit.core.entities.settlementResult.SettlementResult> clearingCorpSettlementResults) {
        List<SettlementResult> settlementResultse =
                new ArrayList<>();

        for (emSeco.clearingCropUnit.core.entities.settlementResult.SettlementResult clearingCorpSettlementResult :
                clearingCorpSettlementResults) {

            SettlementResult settlementResult =
                    new SettlementResult(
                            new Trade(clearingCorpSettlementResult.getTrade().getExchangeId(),
                                    clearingCorpSettlementResult.getTrade().getTradeId(),
                                    clearingCorpSettlementResult.getTrade().getTradeTimestamp(),
                                    mapClearingCorpSideToExchangeTradeSide(
                                            clearingCorpSettlementResult.getTrade().getBuySide()),
                                    mapClearingCorpSideToExchangeTradeSide(
                                            clearingCorpSettlementResult.getTrade().getSellSide())
                            ), clearingCorpSettlementResult.getTradeSettlementTimestamp()
                    );

            settlementResultse.add(settlementResult);
        }
        return settlementResultse;
    }

    private static TradeSide mapClearingCorpSideToCustodianTradeSide
            (emSeco.clearingCropUnit.core.entities.shared.Side side) {

        emSeco.custodianUnit.core.entities.shared.InitiatorType initiatorType;
        if (side.getTradingInformation().getInitiatorType() ==
                emSeco.clearingCropUnit.core.entities.shared.InitiatorType.institutional) {
            initiatorType = InitiatorType.institutional;
        } else {
            initiatorType = InitiatorType.retail;
        }

        return new TradeSide(
                new RoutingInformation(
                        side.getRoutingInformation().getBrokerId(),
                        side.getRoutingInformation().getCustodianId()
                ),
                new AccountsInformation(
                        side.getAccountsInformation().getClearingBankId(),
                        side.getAccountsInformation().getClearingBankAccountNumber(),
                        side.getAccountsInformation().getDepositoryId(),
                        side.getAccountsInformation().getDematAccountNumber()
                ),
                new AllocationDetailInformation(
                        side.getAllocationDetailInformation().getAllocationDetailBlockId(),
                        side.getAllocationDetailInformation().getAllocationDetailId()
                ),
                new TradingInformation(
                        side.getTradingInformation().getInitialOrderId(),
                        initiatorType,
                        side.getTradingInformation().getClientTradingCode(),
                        side.getTradingInformation().getRegisteredCode()
                ),
                new Term(
                        side.getTerm().getPrice(),
                        side.getTerm().getQuantity(),
                        side.getTerm().getInstrumentName()
                )
        );
    }

    private static emSeco.exchangeUnit.core.entities.shared.Side mapClearingCorpSideToExchangeTradeSide
            (emSeco.clearingCropUnit.core.entities.shared.Side side) {

        emSeco.exchangeUnit.core.entities.shared.InitiatorType initiatorType;
        if (side.getTradingInformation().getInitiatorType() ==
                emSeco.clearingCropUnit.core.entities.shared.InitiatorType.institutional) {
            initiatorType = emSeco.exchangeUnit.core.entities.shared.InitiatorType.institutional;
        } else {
            initiatorType = emSeco.exchangeUnit.core.entities.shared.InitiatorType.retail;
        }

        return new emSeco.exchangeUnit.core.entities.shared.Side(
                new emSeco.exchangeUnit.core.entities.shared.RoutingInformation(
                        side.getRoutingInformation().getBrokerId(),
                        side.getRoutingInformation().getCustodianId()
                ),
                new emSeco.exchangeUnit.core.entities.shared.AccountsInformation(
                        side.getAccountsInformation().getClearingBankId(),
                        side.getAccountsInformation().getClearingBankAccountNumber(),
                        side.getAccountsInformation().getDepositoryId(),
                        side.getAccountsInformation().getDematAccountNumber()
                ),
                new emSeco.exchangeUnit.core.entities.shared.AllocationDetailInformation(
                        side.getAllocationDetailInformation().getAllocationDetailBlockId(),
                        side.getAllocationDetailInformation().getAllocationDetailId()
                ),
                new emSeco.exchangeUnit.core.entities.shared.TradingInformation(
                        side.getTradingInformation().getInitialOrderId(),
                        initiatorType,
                        side.getTradingInformation().getClientTradingCode(),
                        side.getTradingInformation().getRegisteredCode()
                ),
                new emSeco.exchangeUnit.core.entities.shared.Term(
                        side.getTerm().getPrice(),
                        side.getTerm().getQuantity(),
                        side.getTerm().getInstrumentName()
                )
        );
    }
}
