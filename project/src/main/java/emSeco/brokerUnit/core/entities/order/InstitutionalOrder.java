package emSeco.brokerUnit.core.entities.order;

import emSeco.brokerUnit.core.entities.shared.EquityTransferMethod;
import emSeco.brokerUnit.core.entities.shared.MoneyTransferMethod;
import emSeco.brokerUnit.core.entities.shared.Term;

import java.util.UUID;

public class InstitutionalOrder extends Order {
    private final UUID registeredCode;

    public InstitutionalOrder(OrderRoutingInformation orderRoutingInformation, UUID orderId,
                              OrderTradingInformation orderTradingInformation, Term term, UUID registeredCode,
                              MoneyTransferMethod moneyTransferMethod, EquityTransferMethod equityTransferMethod) {
        this.orderRoutingInformation = orderRoutingInformation;
        this.orderId = orderId;
        this.orderTradingInformation = orderTradingInformation;
        this.term=term;
        this.registeredCode = registeredCode;
        this.moneyTransferMethod = moneyTransferMethod;
        this.equityTransferMethod = equityTransferMethod;
    }


    public UUID getRegisteredCode() {
        return registeredCode;
    }
}
