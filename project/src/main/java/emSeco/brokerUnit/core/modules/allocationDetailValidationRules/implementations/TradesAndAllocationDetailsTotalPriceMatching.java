package emSeco.brokerUnit.core.modules.allocationDetailValidationRules.implementations;

//#if BrokerTradesAndAllocationDetailsTotalPriceMatching
import com.google.inject.Inject;
import emSeco.brokerUnit.core.entities.allocationDetail.AllocationDetail;
import emSeco.brokerUnit.core.entities.order.InstitutionalOrder;
import emSeco.brokerUnit.core.entities.shared.SideName;
import emSeco.brokerUnit.core.entities.trade.Trade;
import emSeco.brokerUnit.core.modules.allocationDetailValidationRules.interfaces.IAllocationDetailValidationRule;
import emSeco.brokerUnit.infrastructure.services.databases.brokerUnitRepositories.implementations.BrokerUnitRepositories;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;


import java.util.List;

public class TradesAndAllocationDetailsTotalPriceMatching implements IAllocationDetailValidationRule {

    private final BrokerUnitRepositories brokerRepositories;

    @Inject
    public TradesAndAllocationDetailsTotalPriceMatching(BrokerUnitRepositories brokerRepositories) {
        this.brokerRepositories = brokerRepositories;
    }

    @Override
    public BooleanResultMessage checkRule(List<AllocationDetail> allocationDetails) {
        AllocationDetail firstAllocationDetail =
                allocationDetails.stream().findFirst().orElse(null);
        if (firstAllocationDetail == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("There is no allocation details to check!"));
        }

        InstitutionalOrder institutionalOrder = brokerRepositories.getInstitutionalOrdersRepository().
                get(firstAllocationDetail.getTradingInformation().getInitialOrderId());
        if (institutionalOrder == null) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("There is no initial order for these allocation details!"));
        }

        List<Trade> trades = brokerRepositories.getTradeRepository().
                get(firstAllocationDetail.getTradingInformation().getInitialOrderId());

        if (trades.size() == 0) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("There is no trade for these allocation details!"));
        }

        double tradesTotalPrice;
        SideName institutionalOrderOrderSide = institutionalOrder.getTradingInformation().getSideName();
        if (institutionalOrderOrderSide == SideName.buy) {
            tradesTotalPrice = trades.stream().
                    map(allocationDetail -> allocationDetail.getBuySide().getTerm().getTotalPrice()).
                    mapToDouble(Double::doubleValue).sum();
        } else {
            tradesTotalPrice = trades.stream().
                    map(allocationDetail -> allocationDetail.getSellSide().getTerm().getTotalPrice()).
                    mapToDouble(Double::doubleValue).sum();
        }

        double allocationDetailsTotalPrice = allocationDetails.stream().
                map(allocationDetail -> allocationDetail.getTerm().getTotalPrice()).
                mapToDouble(Double::doubleValue).sum();

        if (allocationDetailsTotalPrice != tradesTotalPrice) {
            return new BooleanResultMessage
                    (false, OperationMessage.
                            Create("Trades and allocation details do not match!"));
        }

        return new BooleanResultMessage(true, OperationMessage.emptyOperationMessage());
    }
}
//#endif
