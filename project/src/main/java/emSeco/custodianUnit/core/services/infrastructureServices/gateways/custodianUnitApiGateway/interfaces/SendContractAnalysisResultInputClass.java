package emSeco.custodianUnit.core.services.infrastructureServices.gateways.custodianUnitApiGateway.interfaces;

import emSeco.custodianUnit.core.entities.contract.Contract;

import java.util.List;
import java.util.UUID;

public class SendContractAnalysisResultInputClass {
    private final UUID orderId;
    private final List<Contract> affirmedContracts;
    private final List<Contract> rejectedContracts;

    public SendContractAnalysisResultInputClass
            (UUID orderId, List<Contract> affirmedContracts, List<Contract> rejectedContracts) {
        this.orderId = orderId;
        this.affirmedContracts = affirmedContracts;
        this.rejectedContracts = rejectedContracts;
    }

    public UUID getOrderId() {
        return orderId;
    }
    public List<Contract> getAffirmedContracts() {
        return affirmedContracts;
    }
    public List<Contract> getRejectedContracts() {
        return rejectedContracts;
    }
}
