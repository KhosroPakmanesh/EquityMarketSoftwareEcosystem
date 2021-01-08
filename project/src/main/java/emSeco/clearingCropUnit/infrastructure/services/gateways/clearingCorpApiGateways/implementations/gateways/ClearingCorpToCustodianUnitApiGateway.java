package emSeco.clearingCropUnit.infrastructure.services.gateways.clearingCorpApiGateways.implementations.gateways;

import com.google.inject.Inject;
import emSeco.clearingCropUnit.core.entities.settlementResult.SettlementResult;
import emSeco.clearingCropUnit.core.services.domainServices.clearingCorpServiceRegistry.interfaces.IClearingCorpServiceRegistry;
import emSeco.clearingCropUnit.core.services.infrastructureServices.gateways.clearingCorpApiGateway.interfaces.gateways.IClearingCorpToCustodianUnitApiGateway;
import emSeco.custodianUnit.core.modules.custodian.interfaces.ICustodian;
import emSeco.shared.architecturalConstructs.BooleanResultMessages;
import emSeco.shared.exceptions.ServiceRegistryMalfunctionException;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static emSeco.clearingCropUnit.core.helpers.ClearingCorpToCustodianEntitiesMapper.mapFromClearingCorpToCustodianSettlementResults;

public class ClearingCorpToCustodianUnitApiGateway implements IClearingCorpToCustodianUnitApiGateway {
    private final IClearingCorpServiceRegistry clearingCorpServiceRegistry;

    @Inject
    public ClearingCorpToCustodianUnitApiGateway(IClearingCorpServiceRegistry clearingCorpServiceRegistry) {
        this.clearingCorpServiceRegistry = clearingCorpServiceRegistry;
    }

    @Override
    public List<BooleanResultMessages> submitSettlementResults(List<SettlementResult> clearingCorpSettlementResults) {
        List<BooleanResultMessages> listOfResultMessages = new ArrayList<>();

        List<SettlementResult> settlementResultsWithSameCustodians =
                clearingCorpSettlementResults.stream().filter(settlementResult ->
                        settlementResult.getTrade().getBuySide().getRoutingInformation().getCustodianId() ==
                                settlementResult.getTrade().getSellSide().getRoutingInformation().getCustodianId()).collect(Collectors.toList());

        List<SettlementResult> settlementResultsWithDifferentCustodians =
                clearingCorpSettlementResults.stream().filter(settlementResult ->
                        settlementResult.getTrade().getBuySide().getRoutingInformation().getCustodianId() !=
                                settlementResult.getTrade().getSellSide().getRoutingInformation().getCustodianId()).collect(Collectors.toList());


        settlementResultsWithSameCustodians.stream().collect(Collectors.
                groupingBy(settlementResult -> settlementResult.getTrade().getBuySide().getRoutingInformation().getCustodianId()))
                .forEach((custodianId, groupedSettlementResults) -> {
                    ICustodian sharedCustodian = clearingCorpServiceRegistry.getCustodians().stream().
                            filter(bkr -> bkr.getCustodianId() == custodianId)
                            .findAny().orElse(null);
                    if (sharedCustodian == null) {
                        throw new ServiceRegistryMalfunctionException
                                ("Clearing Corporation's service registry malfunctions!");
                    }

                    listOfResultMessages.add(sharedCustodian.submitSettlementResults_API(
                            mapFromClearingCorpToCustodianSettlementResults(groupedSettlementResults)));
                });

        if (settlementResultsWithDifferentCustodians.stream().anyMatch(settlementResult -> settlementResult.getTrade().getBuySide()
                .getRoutingInformation().getCustodianId() != null)) {
            settlementResultsWithDifferentCustodians.stream().collect(Collectors.
                    groupingBy(tr -> tr.getTrade().getBuySide().getRoutingInformation().getCustodianId()))
                    .forEach((custodianId, groupedSettlementResults) -> {
                        ICustodian buySideCustodian = clearingCorpServiceRegistry.getCustodians().stream().
                                filter(bkr -> bkr.getCustodianId() == custodianId)
                                .findAny().orElse(null);
                        if (buySideCustodian == null) {
                            throw new ServiceRegistryMalfunctionException
                                    ("Clearing Corporation's service registry malfunctions!");
                        }

                        listOfResultMessages.add(buySideCustodian.submitSettlementResults_API(
                                mapFromClearingCorpToCustodianSettlementResults(groupedSettlementResults)));
                    });
        }

        if (settlementResultsWithDifferentCustodians.stream().anyMatch(settlementResult -> settlementResult.getTrade().getSellSide()
                .getRoutingInformation().getCustodianId() != null)) {
            settlementResultsWithDifferentCustodians.stream().collect(Collectors.
                    groupingBy(tr -> tr.getTrade().getSellSide().getRoutingInformation().getCustodianId()))
                    .forEach((custodianId, groupedSettlementResults) -> {
                        ICustodian sellSideCustodian = clearingCorpServiceRegistry.getCustodians().stream().
                                filter(bkr -> bkr.getCustodianId() == custodianId)
                                .findAny().orElse(null);
                        if (sellSideCustodian == null) {
                            throw new ServiceRegistryMalfunctionException
                                    ("Clearing Corporation's service registry malfunctions!");
                        }

                        listOfResultMessages.add(sellSideCustodian.submitSettlementResults_API(
                                mapFromClearingCorpToCustodianSettlementResults(groupedSettlementResults)));
                    });

        }

        return listOfResultMessages;
    }
}
