package emSeco.brokerUnit.core.entities.trade;

import emSeco.brokerUnit.core.entities.shared.*;

public class TradeSide extends Side {
    private boolean clientObligationsDischarged;

    TradeSide(RoutingInformation tradeRoutingInformation,
              AccountsInformation accountsInformation,
              AllocationDetailInformation allocationDetailInformation,
              TradingInformation tradingInformation,
              Term term) {

        super(tradeRoutingInformation, accountsInformation,
                allocationDetailInformation, tradingInformation, term);
    }

    public boolean isClientObligationsDischarged() {
        return clientObligationsDischarged;
    }

    public void clientObligationsDischarged() {
        this.clientObligationsDischarged = true;
    }
}
