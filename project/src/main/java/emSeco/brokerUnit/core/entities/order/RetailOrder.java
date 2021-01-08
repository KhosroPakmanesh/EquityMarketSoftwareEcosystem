package emSeco.brokerUnit.core.entities.order;

import emSeco.brokerUnit.core.entities.shared.AccountsInformation;
import emSeco.brokerUnit.core.entities.shared.EquityTransferMethod;
import emSeco.brokerUnit.core.entities.shared.MoneyTransferMethod;
import emSeco.brokerUnit.core.entities.shared.Term;

import java.util.UUID;

public class RetailOrder extends Order {
    private final UUID clientTradingCode;
    private final AccountsInformation accountsInformation;

    public UUID getClientTradingCode() {
        return clientTradingCode;
    }
    public AccountsInformation getAccountsInformation() {
        return accountsInformation;
    }

    public RetailOrder(OrderRoutingInformation orderRoutingInformation, UUID orderId,
                       AccountsInformation accountsInformation, OrderTradingInformation orderTradingInformation,
                       Term term, UUID clientTradingCode, MoneyTransferMethod moneyTransferMethod,
                       EquityTransferMethod equityTransferMethod) {
        this.equityTransferMethod = equityTransferMethod;
        this.orderRoutingInformation = orderRoutingInformation;
        this.orderId = orderId;
        this.accountsInformation = accountsInformation;
        this.orderTradingInformation = orderTradingInformation;
        this.term=term;
        this.clientTradingCode = clientTradingCode;
        this.moneyTransferMethod = moneyTransferMethod;
    }
}
