package emSeco.custodianUnit.core.entities.shared;


public class Side {
    protected RoutingInformation tradeRoutingInformation;
    protected AccountsInformation accountsInformation;
    protected AllocationDetailInformation allocationDetailInformation;
    protected TradingInformation tradingInformation;
    protected Term term;

    public Side(RoutingInformation routingInformation,
                AccountsInformation accountsInformation,
                AllocationDetailInformation allocationDetailInformation,
                TradingInformation tradingInformation,
                Term term) {
        this.tradeRoutingInformation = routingInformation;
        this.accountsInformation = accountsInformation;
        this.tradingInformation = tradingInformation;
        this.allocationDetailInformation = allocationDetailInformation;
        this.term = term;
    }


    public RoutingInformation getRoutingInformation() {
        return tradeRoutingInformation;
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
}
