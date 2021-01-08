package emSeco.custodianUnit.core.entities.allocationDetail;

import emSeco.custodianUnit.core.entities.shared.*;

public class AllocationDetail {
    private final RoutingInformation routingInformation;

    private final AllocationDetailInformation allocationDetailInformation;
    private final AccountsInformation accountsInformation;
    private final TradingInformation tradingInformation;
    private final Term term;
    private final MoneyTransferMethod moneyTransferMethod;
    private final EquityTransferMethod equityTransferMethod;

    public AllocationDetail(RoutingInformation routingInformation,
                            AllocationDetailInformation allocationDetailInformation,
                            AccountsInformation accountsInformation,
                            TradingInformation tradingInformation,
                            Term term, MoneyTransferMethod moneyTransferMethod,
                            EquityTransferMethod equityTransferMethod) {
        this.routingInformation = routingInformation;
        this.allocationDetailInformation = allocationDetailInformation;
        this.accountsInformation = accountsInformation;
        this.tradingInformation = tradingInformation;
        this.term = term;
        this.moneyTransferMethod = moneyTransferMethod;
        this.equityTransferMethod = equityTransferMethod;
    }

    public RoutingInformation getRoutingInformation() {
        return routingInformation;
    }

    public AllocationDetailInformation getAllocationDetailInformation() {
        return allocationDetailInformation;
    }

    public AccountsInformation getAccountsInformation() {
        return accountsInformation;
    }

    public TradingInformation getTradingInformation() {
        return tradingInformation;
    }

    public Term getTerm() {
        return term;
    }

    public MoneyTransferMethod getMoneyTransferMethod() {
        return moneyTransferMethod;
    }

    public EquityTransferMethod getEquityTransferMethod() {
        return equityTransferMethod;
    }
}
