package emSeco.brokerUnit.core.helpers;

import emSeco.brokerUnit.core.entities.allocationDetail.AllocationDetail;
import emSeco.brokerUnit.core.entities.shared.AllocationDetailInformation;
import emSeco.brokerUnit.core.entities.shared.RoutingInformation;
import emSeco.brokerUnit.core.entities.shared.Term;
import emSeco.brokerUnit.core.entities.shared.TradingInformation;
import emSeco.brokerUnit.core.modules.broker.models.SubmitAllocationDetailsInputClass;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InputClassToBrokerEntitiesMapper {
    public static List<AllocationDetail> mapSubmitAllocationDetailsInputClassToAllocationDetails(
            List<SubmitAllocationDetailsInputClass> inputClasses, UUID brokerId) {

        List<AllocationDetail> allocationDetails = new ArrayList<>();
        for (SubmitAllocationDetailsInputClass submitAllocationDetailsInputClass : inputClasses) {
            allocationDetails.add(
                    new AllocationDetail(
                            new RoutingInformation(
                                    brokerId,
                                    submitAllocationDetailsInputClass.getCustodianId()
                            ),
                            new AllocationDetailInformation(
                                    submitAllocationDetailsInputClass.getAllocationDetailBlockId(),
                                    submitAllocationDetailsInputClass.getAllocationDetailId()
                            ),
                            new TradingInformation(
                                    submitAllocationDetailsInputClass.getInitialOrderId(),
                                    submitAllocationDetailsInputClass.getOrderInitiatorType(),
                                    submitAllocationDetailsInputClass.getClientTradingCode(),
                                    submitAllocationDetailsInputClass.getRegisteredCode()
                            ),
                            new Term(
                                    submitAllocationDetailsInputClass.getPrice(),
                                    submitAllocationDetailsInputClass.getQuantity(),
                                    submitAllocationDetailsInputClass.getInstrumentName()
                            )
                    ));
        }

        return allocationDetails;
    }
}
