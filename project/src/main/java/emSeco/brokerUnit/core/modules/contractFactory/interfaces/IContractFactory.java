package emSeco.brokerUnit.core.modules.contractFactory.interfaces;

import emSeco.brokerUnit.core.entities.contract.Contract;
import emSeco.brokerUnit.core.entities.allocationDetail.AllocationDetail;
import emSeco.brokerUnit.core.entities.shared.SideName;
import emSeco.brokerUnit.core.entities.trade.Trade;

import java.util.List;

public interface IContractFactory {
    List<Contract> constructContracts
            (List<Trade> trades, List<AllocationDetail> allocationDetails, SideName orderSide);
}
