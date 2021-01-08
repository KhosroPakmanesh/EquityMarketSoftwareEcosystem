package emSeco.exchangeUnit.core.helpers;

import emSeco.exchangeUnit.core.entities.order.Order;
import emSeco.exchangeUnit.core.entities.shared.*;

public class ExchangeToExchangeEntitiesMapper {
    public static Side mapFromOrdersToBuyTradeSideForMainBuyOrder
            (Order inProcessBuyOrder, Order storedSellOrder) {

        int tradeAgreedQuantity = getTradeAgreedQuantity(inProcessBuyOrder, storedSellOrder);

        return new Side(
                new RoutingInformation(
                        inProcessBuyOrder.getRoutingInformation().getBrokerId(),
                        inProcessBuyOrder.getRoutingInformation().getCustodianId()
                ),
                new AccountsInformation(
                        inProcessBuyOrder.getAccountsInformation().getClearingBankId(),
                        inProcessBuyOrder.getAccountsInformation().getClearingBankAccountNumber(),
                        inProcessBuyOrder.getAccountsInformation().getDepositoryId(),
                        inProcessBuyOrder.getAccountsInformation().getDematAccountNumber()
                ),
                new AllocationDetailInformation(
                        null,
                        null
                ),
                new TradingInformation(
                        inProcessBuyOrder.getTradingInformation().getInitialOrderId(),
                        inProcessBuyOrder.getInitiatorType(),
                        inProcessBuyOrder.getTradingInformation().getClientTradingCode(),
                        inProcessBuyOrder.getTradingInformation().getRegisteredCode()
                ),
                new Term(
                        storedSellOrder.getTerm().getPrice(),
                        tradeAgreedQuantity,
                        inProcessBuyOrder.getTerm().getInstrumentName()
                ));
    }

    public static Side mapFromOrdersToSellTradeSideForMainBuyOrder
            (Order inProcessBuyOrder, Order storedSellOrder) {

        int tradeAgreedQuantity = getTradeAgreedQuantity(inProcessBuyOrder, storedSellOrder);

        AccountsInformation accountsInformation = null;
        if (storedSellOrder.getAccountsInformation() != null) {
            accountsInformation = new AccountsInformation(
                    storedSellOrder.getAccountsInformation().getClearingBankId(),
                    storedSellOrder.getAccountsInformation().getClearingBankAccountNumber(),
                    storedSellOrder.getAccountsInformation().getDepositoryId(),
                    storedSellOrder.getAccountsInformation().getDematAccountNumber()
            );
        }

        return new Side(
                new RoutingInformation(
                        storedSellOrder.getRoutingInformation().getBrokerId(),
                        storedSellOrder.getRoutingInformation().getCustodianId()
                ),
                accountsInformation,
                new AllocationDetailInformation(
                        null,
                        null
                ),
                new TradingInformation(
                        storedSellOrder.getTradingInformation().getInitialOrderId(),
                        storedSellOrder.getInitiatorType(),
                        storedSellOrder.getTradingInformation().getClientTradingCode(),
                        storedSellOrder.getTradingInformation().getRegisteredCode()
                ),
                new Term(
                        storedSellOrder.getTerm().getPrice(),
                        tradeAgreedQuantity,
                        storedSellOrder.getTerm().getInstrumentName()
                ));
    }

    public static Side mapFromOrdersToSellTradeSideForMainSellOrder
            (Order inProcessSellOrder, Order storedBuyOrder) {
        int tradeAgreedQuantity = getTradeAgreedQuantity(inProcessSellOrder, storedBuyOrder);

        AccountsInformation accountsInformation = null;
        if (storedBuyOrder.getAccountsInformation() != null) {
            accountsInformation = new AccountsInformation(
                    inProcessSellOrder.getAccountsInformation().getClearingBankId(),
                    inProcessSellOrder.getAccountsInformation().getClearingBankAccountNumber(),
                    inProcessSellOrder.getAccountsInformation().getDepositoryId(),
                    inProcessSellOrder.getAccountsInformation().getDematAccountNumber()
            );
        }

        return new Side(
                new RoutingInformation(
                        inProcessSellOrder.getRoutingInformation().getBrokerId(),
                        inProcessSellOrder.getRoutingInformation().getCustodianId()
                ),
                accountsInformation,
                new AllocationDetailInformation(
                        null,
                        null
                ),
                new TradingInformation(
                        inProcessSellOrder.getTradingInformation().getInitialOrderId(),
                        inProcessSellOrder.getInitiatorType(),
                        inProcessSellOrder.getTradingInformation().getClientTradingCode(),
                        inProcessSellOrder.getTradingInformation().getRegisteredCode()
                ),
                new Term(
                        storedBuyOrder.getTerm().getPrice(),
                        tradeAgreedQuantity,
                        inProcessSellOrder.getTerm().getInstrumentName()
                ));
    }

    public static Side mapFromOrdersToBuyTradeSideForMainSellOrder
            (Order inProcessSellOrder, Order storedBuyOrder) {
        int tradeAgreedQuantity = getTradeAgreedQuantity(inProcessSellOrder, storedBuyOrder);

        AccountsInformation accountsInformation = null;
        if (storedBuyOrder.getAccountsInformation() != null) {
            accountsInformation = new AccountsInformation(
                    storedBuyOrder.getAccountsInformation().getClearingBankId(),
                    storedBuyOrder.getAccountsInformation().getClearingBankAccountNumber(),
                    storedBuyOrder.getAccountsInformation().getDepositoryId(),
                    storedBuyOrder.getAccountsInformation().getDematAccountNumber()
            );
        }

        return new Side(

                new RoutingInformation(
                        storedBuyOrder.getRoutingInformation().getBrokerId(),
                        storedBuyOrder.getRoutingInformation().getCustodianId()
                ),
                accountsInformation,
                new AllocationDetailInformation(
                        null,
                        null
                ),
                new TradingInformation(
                        storedBuyOrder.getTradingInformation().getInitialOrderId(),
                        storedBuyOrder.getInitiatorType(),
                        storedBuyOrder.getTradingInformation().getClientTradingCode(),
                        storedBuyOrder.getTradingInformation().getRegisteredCode()
                ),
                new Term(
                        storedBuyOrder.getTerm().getPrice(),
                        tradeAgreedQuantity,
                        storedBuyOrder.getTerm().getInstrumentName()
                ));
    }

    private static int getTradeAgreedQuantity(Order inProcessOrder, Order storedOrder) {
        if (inProcessOrder.getTerm().getQuantity() >=
                storedOrder.getTerm().getQuantity()) {
            return storedOrder.getTerm().getQuantity();
        } else {
            return inProcessOrder.getTerm().getQuantity();
        }
    }
}
