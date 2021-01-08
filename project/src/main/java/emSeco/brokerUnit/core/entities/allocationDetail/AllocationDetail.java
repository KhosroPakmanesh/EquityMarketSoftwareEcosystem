package emSeco.brokerUnit.core.entities.allocationDetail;

import emSeco.brokerUnit.core.entities.shared.AllocationDetailInformation;
import emSeco.brokerUnit.core.entities.shared.RoutingInformation;
import emSeco.brokerUnit.core.entities.shared.Term;
import emSeco.brokerUnit.core.entities.shared.TradingInformation;


public class AllocationDetail {
    private final RoutingInformation RoutingInformation;
    private final AllocationDetailInformation allocationDetailInformation;
    private final TradingInformation tradingInformation;
    private final Term term;

    public AllocationDetail(RoutingInformation routingInformation,
                            AllocationDetailInformation allocationDetailInformation,
                            TradingInformation tradingInformation,
                            Term term) {
        this.RoutingInformation = routingInformation;
        this.tradingInformation = tradingInformation;
        this.allocationDetailInformation = allocationDetailInformation;
        this.term = term;
    }

    public RoutingInformation getRoutingInformation() {
        return RoutingInformation;
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
