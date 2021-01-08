package emSeco.custodianUnit.core.entities.trade;


import emSeco.custodianUnit.core.entities.shared.*;

public class TradeSide extends Side {
    private boolean clientObligationsDischarged;

    public TradeSide(
            RoutingInformation tradeRoutingInformation,
            AccountsInformation accountsInformation,
            AllocationDetailInformation allocationDetailInformation,
            TradingInformation tradingInformation,
            Term term) {
        super(tradeRoutingInformation,accountsInformation,allocationDetailInformation,tradingInformation,term);
        this.tradeRoutingInformation = tradeRoutingInformation;
        this.accountsInformation = accountsInformation;
        this.tradingInformation = tradingInformation;
        this.allocationDetailInformation = allocationDetailInformation;
        this.term=term;
    }

    public boolean getClientObligationsDischarged() {
        return clientObligationsDischarged;
    }

    public void setClientObligationsDischarged() {
        this.clientObligationsDischarged = true;
    }
}
