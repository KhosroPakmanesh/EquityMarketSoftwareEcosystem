package emSeco.brokerUnit.core.modules.orderFactory.interfaces;

import emSeco.brokerUnit.core.entities.order.OrderRoutingInformation;
import emSeco.brokerUnit.core.entities.order.OrderTradingInformation;
import emSeco.brokerUnit.core.entities.shared.AccountsInformation;
import emSeco.brokerUnit.core.entities.shared.EquityTransferMethod;
import emSeco.brokerUnit.core.entities.shared.MoneyTransferMethod;
import emSeco.brokerUnit.core.entities.shared.Term;
import emSeco.brokerUnit.core.modules.orderFactory.models.ConstructInstitutionalOrderOutputClass;
import emSeco.brokerUnit.core.modules.orderFactory.models.ConstructRetailOrderOutputClass;

import java.util.UUID;

public interface IOrderFactory {
    ConstructRetailOrderOutputClass constructRetailOrder(
            OrderRoutingInformation orderRoutingInformation,
            AccountsInformation accountsInformation,
            OrderTradingInformation orderTradingInformation,
            Term orderTerm,
            UUID clientTradingCode,
            MoneyTransferMethod moneyTransferMethod,
            EquityTransferMethod equityTransferMethod);

    ConstructInstitutionalOrderOutputClass constructInstitutionalOrder(
            OrderRoutingInformation orderRoutingInformation,
            OrderTradingInformation orderTradingInformation,
            Term orderTerm,
            UUID registeredCode,
            MoneyTransferMethod moneyTransferMethod,
            EquityTransferMethod equityTransferMethod
    );
}
