package emSeco.custodianUnit.core.helpers;

import emSeco.custodianUnit.core.entities.allocationDetail.AllocationDetail;
import emSeco.custodianUnit.core.entities.shared.*;
import emSeco.custodianUnit.core.modules.custodian.models.SubmitAllocationDetailsInputClass;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InputClassToCustodianEntitiesMapper {
    public static List<AllocationDetail> mapSubmitAllocationDetailsInputClassToAllocationDetails(
            List<SubmitAllocationDetailsInputClass> inputClasses, UUID custodianId) {

        List<AllocationDetail> allocationDetails = new ArrayList<>();
        for (SubmitAllocationDetailsInputClass submitAllocationDetailsInputClass : inputClasses) {
            allocationDetails.add(
                    new AllocationDetail(
                            new RoutingInformation(
                                    submitAllocationDetailsInputClass.getBrokerId(),
                                    custodianId
                            ),
                            new AllocationDetailInformation(
                                    submitAllocationDetailsInputClass.getAllocationDetailBlockId(),
                                    submitAllocationDetailsInputClass.getAllocationDetailId()
                            ),
                            new AccountsInformation(
                                    submitAllocationDetailsInputClass.getClientClearingBankId(),
                                    submitAllocationDetailsInputClass.getClientClearingBankAccountNumber(),
                                    submitAllocationDetailsInputClass.getClientDepositoryId(),
                                    submitAllocationDetailsInputClass.getClientDematAccountNumber()
                            ),
                            new TradingInformation(
                                    submitAllocationDetailsInputClass.getInitialOrderId(),
                                    InitiatorType.institutional,
                                    submitAllocationDetailsInputClass.getClientTradeCode(),
                                    submitAllocationDetailsInputClass.getRegisteredCode()
                            ),
                            new Term(
                                    submitAllocationDetailsInputClass.getPrice(),
                                    submitAllocationDetailsInputClass.getQuantity(),
                                    submitAllocationDetailsInputClass.getInstrumentName()
                            ),
                            submitAllocationDetailsInputClass.getMoneyTransferMethod(),
                            submitAllocationDetailsInputClass.getEquityTransferMethod()
                    ));
        }

        return allocationDetails;
    }
}
