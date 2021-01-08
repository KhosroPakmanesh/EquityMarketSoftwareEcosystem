package emSeco.custodianUnit.core.modules.allocationDetailAnalyzer.implementations;


import com.google.inject.Inject;
import emSeco.custodianUnit.core.entities.allocationDetail.AllocationDetail;
import emSeco.custodianUnit.core.entities.contract.Contract;
import emSeco.custodianUnit.core.entities.shared.SideName;
import emSeco.custodianUnit.core.modules.allocationDetailAffirmationRules.interfaces.IAllocationDetailAffirmationRule;
import emSeco.custodianUnit.core.modules.allocationDetailAnalyzer.interfaces.IAllocationDetailAnalyzer;
import emSeco.custodianUnit.core.modules.allocationDetailAnalyzer.models.AnalyzeAllocationDetailOutputClass;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.OperationMessage;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AllocationDetailAnalyzer implements IAllocationDetailAnalyzer {
    private final Set<IAllocationDetailAffirmationRule> allocationDetailAffirmationRules;

    @Inject
    public AllocationDetailAnalyzer(Set<IAllocationDetailAffirmationRule> allocationDetailAffirmationRules) {
        this.allocationDetailAffirmationRules = allocationDetailAffirmationRules;
    }

    public AnalyzeAllocationDetailOutputClass analyzeAllocationDetail
            (List<Contract> contracts, List<AllocationDetail> allocationDetails, SideName sideName) {
        List<Contract> affirmedContracts = new ArrayList<>();

        List<BooleanResultMessage> finalContractAnalysisResultMessages = new ArrayList<>();

        contracts.forEach(contract -> {
            AllocationDetail peerAllocationDetail;
            if (sideName == SideName.buy) {
                peerAllocationDetail = allocationDetails.
                        stream().filter(allocationDetail ->
                        allocationDetail.getTradingInformation().getClientTradingCode() ==
                                contract.getBuySide().getTradingInformation().getClientTradingCode()).
                        findAny().orElse(null);
            } else {
                peerAllocationDetail = allocationDetails.
                        stream().filter(allocationDetail ->
                        allocationDetail.getTradingInformation().getClientTradingCode() ==
                                contract.getSellSide().getTradingInformation().getClientTradingCode()).
                        findAny().orElse(null);
            }

            if (peerAllocationDetail == null) {
                finalContractAnalysisResultMessages.add(new BooleanResultMessage
                        (false, OperationMessage.
                                Create("There is no peer for contract with the client trading code: "
                                        + contract.getBuySide().getTradingInformation().
                                        getClientTradingCode() + " !")));
                return;
            }

            List<BooleanResultMessage> contractAnalysisResultMessages = new ArrayList<>();
            for (IAllocationDetailAffirmationRule allocationDetailAffirmationRule
                    : allocationDetailAffirmationRules) {
                BooleanResultMessage contractAnalysisResultMessage =
                        allocationDetailAffirmationRule.checkRule(contract, peerAllocationDetail);

                if (!contractAnalysisResultMessage.getOperationResult()) {
                    contractAnalysisResultMessages.add(contractAnalysisResultMessage);
                    break;
                }
            }

            if (contractAnalysisResultMessages.size() == 0) {
                affirmedContracts.add(contract);
            } else {
                finalContractAnalysisResultMessages.addAll(contractAnalysisResultMessages);
            }
        });

        contracts.removeAll(affirmedContracts);
        return new AnalyzeAllocationDetailOutputClass(affirmedContracts, contracts, finalContractAnalysisResultMessages);
    }
}
