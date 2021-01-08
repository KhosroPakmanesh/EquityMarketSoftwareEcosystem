package emSeco.clearingCropUnit.core.modules.twoInstitutionalTradesMatcher.implementations;

import com.google.inject.Inject;
import emSeco.clearingCropUnit.core.entities.shared.*;
import emSeco.clearingCropUnit.core.entities.trade.Trade;
import emSeco.clearingCropUnit.core.modules.twoInstitutionalTradesMatcher.interfaces.ITwoInstitutionalTradesMatcher;
import emSeco.shared.services.dateTimeService.interfaces.IDateTimeService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TwoInstitutionalTradesMatcher implements ITwoInstitutionalTradesMatcher {

    private final IDateTimeService dateTimeService;

    @Inject
    public TwoInstitutionalTradesMatcher(IDateTimeService dateTimeService) {
        this.dateTimeService = dateTimeService;
    }

    public List<Trade> MatchTwoTradeSides(List<Trade> trades) {
        List<Trade> buySideTrades = trades.stream().
                filter(trade -> trade.getBuySide().getAllocationDetailInformation()
                        .getAllocationDetailBlockId() != null)
                .collect(Collectors.toList());

        List<Trade> sellSideTrades = trades.stream().
                filter(trade -> trade.getSellSide().getAllocationDetailInformation().
                        getAllocationDetailBlockId() != null).
                collect(Collectors.toList());

        List<Trade> matchedTrades = new ArrayList<>();

        for (Trade buySideTrade : buySideTrades) {

            for (Trade sellSideTrade : sellSideTrades) {

                if (buySideTrade.getBuySide().getTerm().getQuantity() > 0) {
                    if (buySideTrade.getBuySide().getTerm().getQuantity() >=
                            sellSideTrade.getSellSide().getTerm().getQuantity()) {

                        matchedTrades.add(new Trade(
                                buySideTrade.getExchangeId(),
                                buySideTrade.getTradeId(),
                                dateTimeService.getDateTime(),
                                new Side(
                                        new RoutingInformation(
                                                buySideTrade.getBuySide().getRoutingInformation().getBrokerId(),
                                                buySideTrade.getBuySide().getRoutingInformation().getCustodianId()
                                        ),
                                        new AccountsInformation(
                                                buySideTrade.getBuySide().getAccountsInformation().getClearingBankId(),
                                                buySideTrade.getBuySide().getAccountsInformation().getClearingBankAccountNumber(),
                                                buySideTrade.getBuySide().getAccountsInformation().getDepositoryId(),
                                                buySideTrade.getBuySide().getAccountsInformation().getDematAccountNumber()
                                        ),
                                        new AllocationDetailInformation(
                                                buySideTrade.getBuySide().getAllocationDetailInformation().getAllocationDetailBlockId(),
                                                buySideTrade.getBuySide().getAllocationDetailInformation().getAllocationDetailId()
                                        ),
                                        new TradingInformation(
                                                buySideTrade.getBuySide().getTradingInformation().getInitialOrderId(),
                                                buySideTrade.getBuySide().getTradingInformation().getInitiatorType(),
                                                buySideTrade.getBuySide().getTradingInformation().getClientTradingCode(),
                                                buySideTrade.getBuySide().getTradingInformation().getRegisteredCode()
                                        ),
                                        new Term(
                                                buySideTrade.getBuySide().getTerm().getPrice(),
                                                sellSideTrade.getSellSide().getTerm().getQuantity(),
                                                buySideTrade.getBuySide().getTerm().getInstrumentName()
                                        )),
                                new Side(
                                        new RoutingInformation(
                                                sellSideTrade.getSellSide().getRoutingInformation().getBrokerId(),
                                                sellSideTrade.getSellSide().getRoutingInformation().getCustodianId()
                                        ),
                                        new AccountsInformation(
                                                sellSideTrade.getSellSide().getAccountsInformation().getClearingBankId(),
                                                sellSideTrade.getSellSide().getAccountsInformation().getClearingBankAccountNumber(),
                                                sellSideTrade.getSellSide().getAccountsInformation().getDepositoryId(),
                                                sellSideTrade.getSellSide().getAccountsInformation().getDematAccountNumber()
                                        ),
                                        new AllocationDetailInformation(
                                                sellSideTrade.getSellSide().getAllocationDetailInformation().getAllocationDetailBlockId(),
                                                sellSideTrade.getSellSide().getAllocationDetailInformation().getAllocationDetailId()
                                        ),
                                        new TradingInformation(
                                                sellSideTrade.getSellSide().getTradingInformation().getInitialOrderId(),
                                                sellSideTrade.getSellSide().getTradingInformation().getInitiatorType(),
                                                sellSideTrade.getSellSide().getTradingInformation().getClientTradingCode(),
                                                sellSideTrade.getSellSide().getTradingInformation().getRegisteredCode()
                                        ),
                                        new Term(
                                                sellSideTrade.getSellSide().getTerm().getPrice(),
                                                sellSideTrade.getSellSide().getTerm().getQuantity(),
                                                sellSideTrade.getSellSide().getTerm().getInstrumentName()
                                        ))));

                        buySideTrade.getBuySide().getTerm().setQuantity(
                                buySideTrade.getBuySide().getTerm().getQuantity() -
                                        sellSideTrade.getSellSide().getTerm().getQuantity());

                        sellSideTrade.getSellSide().getTerm().setQuantity(0);
                    } else {

                        matchedTrades.add(new Trade(
                                buySideTrade.getExchangeId(),
                                buySideTrade.getTradeId(),
                                dateTimeService.getDateTime(),
                                new Side(
                                        new RoutingInformation(
                                                buySideTrade.getBuySide().getRoutingInformation().getBrokerId(),
                                                buySideTrade.getBuySide().getRoutingInformation().getCustodianId()
                                        ),
                                        new AccountsInformation(
                                                buySideTrade.getBuySide().getAccountsInformation().getClearingBankId(),
                                                buySideTrade.getBuySide().getAccountsInformation().getClearingBankAccountNumber(),
                                                buySideTrade.getBuySide().getAccountsInformation().getDepositoryId(),
                                                buySideTrade.getBuySide().getAccountsInformation().getDematAccountNumber()
                                        ),
                                        new AllocationDetailInformation(
                                                buySideTrade.getBuySide().getAllocationDetailInformation().getAllocationDetailBlockId(),
                                                buySideTrade.getBuySide().getAllocationDetailInformation().getAllocationDetailId()
                                        ),
                                        new TradingInformation(
                                                buySideTrade.getBuySide().getTradingInformation().getInitialOrderId(),
                                                buySideTrade.getBuySide().getTradingInformation().getInitiatorType(),
                                                buySideTrade.getBuySide().getTradingInformation().getClientTradingCode(),
                                                buySideTrade.getBuySide().getTradingInformation().getRegisteredCode()
                                        ),
                                        new Term(
                                                buySideTrade.getBuySide().getTerm().getPrice(),
                                                buySideTrade.getBuySide().getTerm().getQuantity(),
                                                buySideTrade.getBuySide().getTerm().getInstrumentName()
                                        )),
                                new Side(
                                        new RoutingInformation(
                                                sellSideTrade.getSellSide().getRoutingInformation().getBrokerId(),
                                                sellSideTrade.getSellSide().getRoutingInformation().getCustodianId()
                                        ),
                                        new AccountsInformation(
                                                sellSideTrade.getSellSide().getAccountsInformation().getClearingBankId(),
                                                sellSideTrade.getSellSide().getAccountsInformation().getClearingBankAccountNumber(),
                                                sellSideTrade.getSellSide().getAccountsInformation().getDepositoryId(),
                                                sellSideTrade.getSellSide().getAccountsInformation().getDematAccountNumber()
                                        ),
                                        new AllocationDetailInformation(
                                                sellSideTrade.getSellSide().getAllocationDetailInformation().getAllocationDetailBlockId(),
                                                sellSideTrade.getSellSide().getAllocationDetailInformation().getAllocationDetailId()
                                        ),
                                        new TradingInformation(
                                                sellSideTrade.getSellSide().getTradingInformation().getInitialOrderId(),
                                                sellSideTrade.getSellSide().getTradingInformation().getInitiatorType(),
                                                sellSideTrade.getSellSide().getTradingInformation().getClientTradingCode(),
                                                sellSideTrade.getSellSide().getTradingInformation().getRegisteredCode()
                                        ),
                                        new Term(
                                                sellSideTrade.getSellSide().getTerm().getPrice(),
                                                buySideTrade.getBuySide().getTerm().getQuantity(),
                                                sellSideTrade.getSellSide().getTerm().getInstrumentName()
                                        ))));

                        sellSideTrade.getSellSide().getTerm().setQuantity(
                                sellSideTrade.getSellSide().getTerm().getQuantity() -
                                        buySideTrade.getBuySide().getTerm().getQuantity());

                        buySideTrade.getBuySide().getTerm().setQuantity(0);
                    }
                } else {
                    break;
                }

            }

            sellSideTrades.removeIf(ad -> ad.getSellSide().getTerm().getQuantity() == 0);
        }

        return matchedTrades;
    }
}