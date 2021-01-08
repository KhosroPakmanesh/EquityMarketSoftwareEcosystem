package emSeco.custodianUnit.core.modules.allocationDetailAnalyzer.models;

import emSeco.custodianUnit.core.entities.contract.Contract;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;


import java.util.List;

public class AnalyzeAllocationDetailOutputClass {
    private final List<Contract> affirmedContracts;
    private final List<Contract> notAffirmedContracts;

    private final List<BooleanResultMessage> failureResultMessages;

    public AnalyzeAllocationDetailOutputClass
            (List<Contract> affirmedContracts,
             List<Contract> notAffirmedContracts,
             List<BooleanResultMessage> failureResultMessages) {
        this.affirmedContracts = affirmedContracts;
        this.notAffirmedContracts = notAffirmedContracts;
        this.failureResultMessages = failureResultMessages;
    }

    public List<Contract> getAffirmedContracts() {
        return affirmedContracts;
    }

    public List<Contract> getNotAffirmedContracts() {
        return notAffirmedContracts;
    }

    public List<BooleanResultMessage> getFailureResultMessages() {
        return failureResultMessages;
    }
}
