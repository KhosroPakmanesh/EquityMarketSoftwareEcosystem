package emSeco.brokerUnit.core.helpers;

import emSeco.brokerUnit.core.entities.contract.Contract;
import emSeco.brokerUnit.core.entities.shared.SideName;
import emSeco.custodianUnit.core.entities.shared.*;
import emSeco.custodianUnit.core.entities.trade.Trade;
import emSeco.custodianUnit.core.entities.trade.TradeSide;

import java.util.ArrayList;
import java.util.List;

public class BrokerToCustodianEntitiesMapper {
    public static List<Trade> MapFromBrokerTradeToCustodianTrade
            (List<emSeco.brokerUnit.core.entities.trade.Trade> brokerTrades) {
        List<emSeco.custodianUnit.core.entities.trade.Trade> custodianTrades = new ArrayList<>();

        for (emSeco.brokerUnit.core.entities.trade.Trade brokerTrade : brokerTrades) {

            emSeco.custodianUnit.core.entities.shared.SideName tradeOwnerSideName;
            if (brokerTrade.getTradeOwnerSideName() == SideName.buy) {
                tradeOwnerSideName = emSeco.custodianUnit.core.entities.shared.SideName.buy;
            } else {
                tradeOwnerSideName = emSeco.custodianUnit.core.entities.shared.SideName.sell;
            }

            custodianTrades.add(new emSeco.custodianUnit.core.entities.trade.Trade(
                    brokerTrade.getExchangeId(),
                    brokerTrade.getTradeId(),
                    brokerTrade.getTradeTimestamp(),
                    mapBrokerSideToCustodianTradeSide(brokerTrade.getBuySide()),
                    mapBrokerSideToCustodianTradeSide(brokerTrade.getSellSide()),
                    tradeOwnerSideName
            ));
        }

        return custodianTrades;
    }

    public static List<emSeco.custodianUnit.core.entities.contract.Contract>
    MapFromBrokerContractToCustodianContract(List<Contract> brokerContracts) {
        List<emSeco.custodianUnit.core.entities.contract.Contract> custodianContracts = new ArrayList<>();

        brokerContracts.forEach(brokerContract -> custodianContracts.add(
                new emSeco.custodianUnit.core.entities.contract.Contract(
                        brokerContract.getExchangeId(),
                        brokerContract.getInitialTradeId(),
                        brokerContract.getContractId(),
                        brokerContract.getTradeTimestamp(),
                        mapBrokerSideToCustodianSide(brokerContract.getBuySide()),
                        mapBrokerSideToCustodianSide(brokerContract.getSellSide())
                )));

        return custodianContracts;
    }

    private static TradeSide mapBrokerSideToCustodianTradeSide
            (emSeco.brokerUnit.core.entities.shared.Side side) {
        InitiatorType initiatorType;
        if (side.getTradingInformation().getInitiatorType() ==
                emSeco.brokerUnit.core.entities.shared.InitiatorType.institutional) {
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

    private static emSeco.custodianUnit.core.entities.shared.Side mapBrokerSideToCustodianSide
            (emSeco.brokerUnit.core.entities.shared.Side side) {
        InitiatorType initiatorType;
        if (side.getTradingInformation().getInitiatorType() ==
                emSeco.brokerUnit.core.entities.shared.InitiatorType.institutional) {
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
}