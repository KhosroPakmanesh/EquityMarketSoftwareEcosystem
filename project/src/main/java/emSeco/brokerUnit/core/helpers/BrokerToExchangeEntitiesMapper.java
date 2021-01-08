package emSeco.brokerUnit.core.helpers;

import emSeco.brokerUnit.core.entities.order.InstitutionalOrder;
import emSeco.brokerUnit.core.entities.order.RetailOrder;
import emSeco.brokerUnit.core.entities.brokerUnitInfo.BrokerUnitInfo;
import emSeco.exchangeUnit.core.entities.order.Order;
import emSeco.exchangeUnit.core.entities.order.OrderTradingInformation;
import emSeco.exchangeUnit.core.entities.shared.AccountsInformation;
import emSeco.exchangeUnit.core.entities.shared.RoutingInformation;
import emSeco.exchangeUnit.core.entities.shared.SideName;
import emSeco.exchangeUnit.core.entities.shared.Term;

import java.util.Date;

public class BrokerToExchangeEntitiesMapper {
    public static Order MapFromBrokerRetailOrderAndBrokerUnitInfoToExchangeOrder
            (RetailOrder retailOrder, BrokerUnitInfo brokerUnitInfo, Date orderSubmissionDateTime) {

        emSeco.brokerUnit.core.entities.shared.SideName brokerSideName =
                retailOrder.getTradingInformation().getSideName();

        SideName exchangeSideName;
        if (brokerSideName == emSeco.brokerUnit.core.entities.shared.SideName.buy) {
            exchangeSideName = SideName.buy;
        } else {
            exchangeSideName = SideName.sell;
        }

        return new Order(
                new RoutingInformation(
                        retailOrder.getRoutingInformation().getBrokerId()
                ),
                new AccountsInformation(
                        brokerUnitInfo.getAccountsInformation().getClearingBankId(),
                        brokerUnitInfo.getAccountsInformation().getClearingBankAccountNumber(),
                        brokerUnitInfo.getAccountsInformation().getDepositoryId(),
                        brokerUnitInfo.getAccountsInformation().getDematAccountNumber()
                ),
                new OrderTradingInformation(
                        retailOrder.getOrderId(),
                        exchangeSideName,
                        retailOrder.getTradingInformation().getOrderType().name(),
                        null,
                        retailOrder.getClientTradingCode(),
                        retailOrder.getQuantityDisclosureStatus()
                ),
                new Term(
                        retailOrder.getTerm().getPrice(),
                        retailOrder.getTerm().getQuantity(),
                        retailOrder.getTerm().getInstrumentName()
                ),
                orderSubmissionDateTime);
    }

    public static Order MapFromBrokerInstitutionalOrderAndBrokerUnitInfoToExchangeOrder
            (InstitutionalOrder institutionalOrder, Date orderSubmissionDateTime) {

        emSeco.brokerUnit.core.entities.shared.SideName brokerSideName =
                institutionalOrder.getTradingInformation().getSideName();

        SideName exchangeSideName;
        if (brokerSideName == emSeco.brokerUnit.core.entities.shared.SideName.buy) {
            exchangeSideName = SideName.buy;
        } else {
            exchangeSideName = SideName.sell;
        }

        return new Order(
                new RoutingInformation(
                        institutionalOrder.getRoutingInformation().getBrokerId(),
                        institutionalOrder.getRoutingInformation().getCustodianId()
                ),
                new AccountsInformation(
                        null,
                        null,
                        null,
                        null
                ),
                new OrderTradingInformation(
                        institutionalOrder.getOrderId(),
                        exchangeSideName,
                        institutionalOrder.getTradingInformation().getOrderType().name(),
                        institutionalOrder.getRegisteredCode(),
                        null,
                        institutionalOrder.getQuantityDisclosureStatus()
                ),
                new Term(
                        institutionalOrder.getTerm().getPrice(),
                        institutionalOrder.getTerm().getQuantity(),
                        institutionalOrder.getTerm().getInstrumentName()
                ),
                orderSubmissionDateTime);
    }
}