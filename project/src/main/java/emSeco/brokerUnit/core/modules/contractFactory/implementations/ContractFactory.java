package emSeco.brokerUnit.core.modules.contractFactory.implementations;

import com.google.inject.Inject;
import emSeco.brokerUnit.core.entities.contract.Contract;
import emSeco.brokerUnit.core.entities.allocationDetail.AllocationDetail;
import emSeco.brokerUnit.core.entities.shared.SideName;
import emSeco.brokerUnit.core.entities.trade.Trade;
import emSeco.brokerUnit.core.entities.shared.Side;
import emSeco.brokerUnit.core.modules.contractFactory.interfaces.IContractFactory;
import emSeco.shared.services.dateTimeService.implementations.DateTimeService;
import emSeco.shared.services.uuidGenerator.implementations.UUIDGenerator;

import java.util.ArrayList;
import java.util.List;


import static emSeco.brokerUnit.core.helpers.BrokerToBrokerEntitiesMapper.mapFromAllocationDetailAndTradeToContract;

public class ContractFactory implements IContractFactory {
    private final UUIDGenerator uuidGenerator;
    private final DateTimeService dateTimeService;

    @Inject
    public ContractFactory(UUIDGenerator uuidGenerator,
                           DateTimeService dateTimeService) {
        this.uuidGenerator = uuidGenerator;
        this.dateTimeService = dateTimeService;
    }

    @Override
    public List<Contract> constructContracts
            (List<Trade> trades, List<AllocationDetail> allocationDetails, SideName orderSide) {

        return createContracts(trades, allocationDetails, orderSide);
    }

    private List<Contract> createContracts
            (List<Trade> trades, List<AllocationDetail> allocationDetails, SideName orderSide) {

        List<Contract> contracts = new ArrayList<>();

        for (Trade trade : trades) {
            for (AllocationDetail allocationDetail : allocationDetails) {
                Side tradeSide;
                if (orderSide == SideName.buy) {
                    tradeSide = trade.getBuySide();
                } else {
                    tradeSide = trade.getSellSide();
                }

                if (tradeSide.getTerm().getQuantity() > 0) {
                    if (tradeSide.getTerm().getQuantity() >= allocationDetail.getTerm().getQuantity()) {

                        contracts.add(mapFromAllocationDetailAndTradeToContract
                                (allocationDetail, trade, orderSide, uuidGenerator.generateUUID()
                                        , dateTimeService.getDateTime()));

                        tradeSide.getTerm().setQuantity(
                                tradeSide.getTerm().getQuantity() -
                                        allocationDetail.getTerm().getQuantity());

                        allocationDetail.getTerm().setQuantity(0);
                    } else {

                        contracts.add(mapFromAllocationDetailAndTradeToContract
                                (allocationDetail, trade, orderSide, uuidGenerator.generateUUID()
                                        , dateTimeService.getDateTime()));

                        allocationDetail.getTerm().setQuantity(
                                allocationDetail.getTerm().getQuantity() -
                                        tradeSide.getTerm().getQuantity());

                        tradeSide.getTerm().setQuantity(0);
                    }
                } else {
                    break;
                }
            }

            allocationDetails.removeIf(ad -> ad.getTerm().getQuantity() == 0);
        }

        return contracts;
    }
}