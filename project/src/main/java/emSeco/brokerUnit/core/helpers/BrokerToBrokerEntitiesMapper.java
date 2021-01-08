package emSeco.brokerUnit.core.helpers;

import emSeco.brokerUnit.core.entities.noticeOfExecution.NoticeOfExecution;
import emSeco.brokerUnit.core.entities.allocationDetail.AllocationDetail;
import emSeco.brokerUnit.core.entities.contract.Contract;
import emSeco.brokerUnit.core.entities.shared.*;
import emSeco.brokerUnit.core.entities.trade.Trade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class BrokerToBrokerEntitiesMapper {

    public static Contract mapFromAllocationDetailAndTradeToContract
            (AllocationDetail allocationDetail, Trade trade, SideName orderSide,
             UUID contractId, Date contractTimestamp) {

        if (orderSide == SideName.buy) {

            int quantity;
            if (trade.getBuySide().getTerm().getQuantity() >= allocationDetail.getTerm().getQuantity()) {
                quantity = allocationDetail.getTerm().getQuantity();
            } else {
                quantity = trade.getBuySide().getTerm().getQuantity();
            }

            return new Contract(
                    trade.getExchangeId(),
                    trade.getTradeId(),
                    contractId,
                    contractTimestamp,
                    new Side(
                            new RoutingInformation(
                                    trade.getBuySide().getRoutingInformation().getBrokerId(),
                                    allocationDetail.getRoutingInformation().getCustodianId()),
                            new AccountsInformation(
                                    null,
                                    null,
                                    null,
                                    null
                            ),
                            allocationDetail.getAllocationDetailInformation(),
                            allocationDetail.getTradingInformation(),
                            new Term(
                                    allocationDetail.getTerm().getPrice(),
                                    quantity,
                                    allocationDetail.getTerm().getInstrumentName())),
                    new Side(
                            trade.getSellSide().getRoutingInformation(),
                            trade.getSellSide().getAccountsInformation(),
                            trade.getSellSide().getAllocationDetailInformation(),
                            trade.getSellSide().getTradingInformation(),
                            new Term(
                                    trade.getSellSide().getTerm().getPrice(),
                                    quantity,
                                    trade.getSellSide().getTerm().getInstrumentName())),
                    SideName.buy
            );

        } else {

            int quantity;
            if (trade.getSellSide().getTerm().getQuantity() >= allocationDetail.getTerm().getQuantity()) {
                quantity = allocationDetail.getTerm().getQuantity();
            } else {
                quantity = trade.getSellSide().getTerm().getQuantity();
            }

            return new Contract(
                    trade.getExchangeId(),
                    trade.getTradeId(),
                    contractId,
                    contractTimestamp,
                    new Side(
                            trade.getBuySide().getRoutingInformation(),
                            trade.getBuySide().getAccountsInformation(),
                            trade.getBuySide().getAllocationDetailInformation(),
                            trade.getBuySide().getTradingInformation(),
                            new Term(
                                    trade.getBuySide().getTerm().getPrice(),
                                    quantity,
                                    trade.getBuySide().getTerm().getInstrumentName())),
                    new Side(
                            new RoutingInformation(
                                    trade.getSellSide().getRoutingInformation().getBrokerId(),
                                    allocationDetail.getRoutingInformation().getCustodianId()),
                            new AccountsInformation(
                                    null,
                                    null,
                                    null,
                                    null
                            ),
                            allocationDetail.getAllocationDetailInformation(),
                            allocationDetail.getTradingInformation(),
                            new Term(
                                    allocationDetail.getTerm().getPrice(),
                                    quantity,
                                    allocationDetail.getTerm().getInstrumentName())
                    ),
                    SideName.sell);
        }
    }

    public static List<NoticeOfExecution> mapFromTradesToNoticeOfExecutions(List<Trade> trades) {
        List<NoticeOfExecution> noticeOfExecutions = new ArrayList<>();

        for (Trade trade : trades) {
            noticeOfExecutions.add(
                    new NoticeOfExecution(trade.getExchangeId(),
                            trade.getTradeId(),
                            trade.getTradeTimestamp(),
                            new Side(
                                    trade.getBuySide().getRoutingInformation(),
                                    trade.getBuySide().getAccountsInformation(),
                                    trade.getBuySide().getAllocationDetailInformation(),
                                    trade.getBuySide().getTradingInformation(),
                                    new Term(trade.getBuySide().getTerm().getPrice(),
                                            trade.getBuySide().getTerm().getQuantity(),
                                            trade.getBuySide().getTerm().getInstrumentName())),
                            new Side(
                                    trade.getSellSide().getRoutingInformation(),
                                    trade.getSellSide().getAccountsInformation(),
                                    trade.getSellSide().getAllocationDetailInformation(),
                                    trade.getSellSide().getTradingInformation(),
                                    new Term(trade.getSellSide().getTerm().getPrice(),
                                            trade.getSellSide().getTerm().getQuantity(),
                                            trade.getSellSide().getTerm().getInstrumentName()))
                    ));
        }

        return noticeOfExecutions;
    }

    public static List<Trade> convertContractsToTrades
            (List<Contract> affirmedContracts, Date conversionDate) {
        List<Trade> trades = new ArrayList<>();

        for (Contract affirmedContract : affirmedContracts) {

            trades.add(
                    new Trade(
                            affirmedContract.getExchangeId(),
                            affirmedContract.getInitialTradeId(),
                            conversionDate,
                            new Side(
                                    affirmedContract.getBuySide().getRoutingInformation(),
                                    affirmedContract.getBuySide().getAccountsInformation(),
                                    affirmedContract.getBuySide().getAllocationDetailInformation(),
                                    affirmedContract.getBuySide().getTradingInformation(),
                                    new Term(affirmedContract.getBuySide().getTerm().getPrice(),
                                            affirmedContract.getBuySide().getTerm().getQuantity(),
                                            affirmedContract.getBuySide().getTerm().getInstrumentName())),
                            new Side(
                                    affirmedContract.getSellSide().getRoutingInformation(),
                                    affirmedContract.getSellSide().getAccountsInformation(),
                                    affirmedContract.getSellSide().getAllocationDetailInformation(),
                                    affirmedContract.getSellSide().getTradingInformation(),
                                    new Term(affirmedContract.getSellSide().getTerm().getPrice(),
                                            affirmedContract.getSellSide().getTerm().getQuantity(),
                                            affirmedContract.getSellSide().getTerm().getInstrumentName())),
                            affirmedContract.getContractOwnerSideName())
            );
        }

        return trades;
    }
}