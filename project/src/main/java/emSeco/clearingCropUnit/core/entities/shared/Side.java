package emSeco.clearingCropUnit.core.entities.shared;


public class Side {
    protected final RoutingInformation routingInformation;
    protected final AccountsInformation accountsInformation;
    protected final AllocationDetailInformation allocationDetailInformation;
    protected final TradingInformation tradingInformation;
    protected final Term term;

    public Side(RoutingInformation routingInformation,
                AccountsInformation accountsInformation,
                AllocationDetailInformation allocationDetailInformation,
                TradingInformation tradingInformation,
                Term term) {
        this.routingInformation = routingInformation;
        this.accountsInformation = accountsInformation;
        this.tradingInformation = tradingInformation;
        this.allocationDetailInformation = allocationDetailInformation;
        this.term = term;
    }

    public RoutingInformation getRoutingInformation() {
        return routingInformation;
    }

    public AccountsInformation getAccountsInformation() {
        return accountsInformation;
    }

    public AllocationDetailInformation getAllocationDetailInformation() {
        return allocationDetailInformation;
    }

    public TradingInformation getTradingInformation() {
        return tradingInformation;
    }

    public Term getTerm() {
        return term;
    }

    public boolean isInstitutional() {
        return routingInformation.getCustodianId() != null;
    }
}
