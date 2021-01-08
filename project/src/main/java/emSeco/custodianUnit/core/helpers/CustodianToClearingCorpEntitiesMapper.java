package emSeco.custodianUnit.core.helpers;

import emSeco.clearingCropUnit.core.entities.shared.*;
import emSeco.clearingCropUnit.core.entities.trade.Trade;
import emSeco.custodianUnit.core.entities.shared.InitiatorType;
import emSeco.custodianUnit.core.entities.custodianUnitInfo.CustodianUnitInfo;
import emSeco.custodianUnit.core.entities.shared.SideName;

import java.util.ArrayList;
import java.util.List;


public class CustodianToClearingCorpEntitiesMapper {
    public static List<Trade> mapCustodianTradesToClearingCorpTrades
            (List<emSeco.custodianUnit.core.entities.trade.Trade> trades, CustodianUnitInfo custodianUnitInfo) {
        List<emSeco.clearingCropUnit.core.entities.trade.Trade> clearingCorpTrades = new ArrayList<>();

        for (emSeco.custodianUnit.core.entities.trade.Trade custodianTrade : trades) {

            emSeco.clearingCropUnit.core.entities.shared.SideName tradeOwnerSideName;
            if (custodianTrade.getTradeOwnerSideName() == SideName.buy) {
                tradeOwnerSideName = emSeco.clearingCropUnit.core.entities.shared.SideName.buy;
            } else {
                tradeOwnerSideName = emSeco.clearingCropUnit.core.entities.shared.SideName.sell;
            }

            clearingCorpTrades.add(
                    new emSeco.clearingCropUnit.core.entities.trade.Trade(
                            custodianTrade.getExchangeId(),
                            custodianTrade.getTradeId(),
                            custodianTrade.getTradeTimestamp(),
                            mapCustodianSideToClearingCorpSide(custodianTrade.getBuySide(), custodianUnitInfo),
                            mapCustodianSideToClearingCorpSide(custodianTrade.getSellSide(), custodianUnitInfo),
                            tradeOwnerSideName
                    ));
        }

        return clearingCorpTrades;
    }

    private static Side mapCustodianSideToClearingCorpSide
            (emSeco.custodianUnit.core.entities.trade.TradeSide tradeSide, CustodianUnitInfo custodianUnitInfo) {

        emSeco.clearingCropUnit.core.entities.shared.InitiatorType initiatorType;
        if (tradeSide.getTradingInformation().getInitiatorType() ==
                InitiatorType.institutional) {
            initiatorType = emSeco.clearingCropUnit.core.entities.shared.InitiatorType.institutional;
        } else {
            initiatorType = emSeco.clearingCropUnit.core.entities.shared.InitiatorType.retail;
        }

        AccountsInformation accountsInformation = new AccountsInformation(
                tradeSide.getAccountsInformation().getClearingBankId(),
                tradeSide.getAccountsInformation().getClearingBankAccountNumber(),
                tradeSide.getAccountsInformation().getDepositoryId(),
                tradeSide.getAccountsInformation().getDematAccountNumber()
        );
        if (custodianUnitInfo.getCustodianId().
                equals(tradeSide.getRoutingInformation().getCustodianId())) {
            accountsInformation = new AccountsInformation(
                    custodianUnitInfo.getAccountsInformation().getClearingBankId(),
                    custodianUnitInfo.getAccountsInformation().getClearingBankAccountNumber(),
                    custodianUnitInfo.getAccountsInformation().getDepositoryId(),
                    custodianUnitInfo.getAccountsInformation().getDematAccountNumber()
            );
        }

        return new Side(
                new RoutingInformation(
                        tradeSide.getRoutingInformation().getBrokerId(),
                        tradeSide.getRoutingInformation().getCustodianId()
                ),
                accountsInformation,
                new AllocationDetailInformation(
                        tradeSide.getAllocationDetailInformation().getAllocationDetailBlockId(),
                        tradeSide.getAllocationDetailInformation().getAllocationDetailId()
                ),
                new TradingInformation(
                        tradeSide.getTradingInformation().getInitialOrderId(),
                        initiatorType,
                        tradeSide.getTradingInformation().getClientTradingCode(),
                        tradeSide.getTradingInformation().getRegisteredCode()
                ),
                new Term(
                        tradeSide.getTerm().getPrice(),
                        tradeSide.getTerm().getQuantity(),
                        tradeSide.getTerm().getInstrumentName()
                )
        );
    }
}
