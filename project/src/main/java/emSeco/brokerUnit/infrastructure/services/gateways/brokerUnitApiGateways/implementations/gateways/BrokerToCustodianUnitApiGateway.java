package emSeco.brokerUnit.infrastructure.services.gateways.brokerUnitApiGateways.implementations.gateways;

import com.google.inject.Inject;
import emSeco.brokerUnit.core.entities.contract.Contract;
import emSeco.brokerUnit.core.entities.trade.Trade;
import emSeco.brokerUnit.core.services.domainServices.brokerServiceRegistry.interfaces.IBrokerServiceRegistry;
import emSeco.brokerUnit.core.services.infrastructureServices.gateways.brokerUnitApiGateways.interfaces.gateways.IBrokerToCustodianUnitApiGateway;
import emSeco.custodianUnit.core.modules.custodian.interfaces.ICustodian;
import emSeco.shared.architecturalConstructs.BooleanResultMessages;

import java.util.*;
import java.util.stream.Collectors;

import static emSeco.brokerUnit.core.helpers.BrokerToCustodianEntitiesMapper.MapFromBrokerContractToCustodianContract;
import static emSeco.brokerUnit.core.helpers.BrokerToCustodianEntitiesMapper.MapFromBrokerTradeToCustodianTrade;
import static emSeco.shared.architecturalConstructs.BooleanResultMessages.aggregateListOfResultMessages;

public class BrokerToCustodianUnitApiGateway implements IBrokerToCustodianUnitApiGateway {
    private final IBrokerServiceRegistry brokerServiceRegistry;

    @Inject
    public BrokerToCustodianUnitApiGateway(IBrokerServiceRegistry brokerServiceRegistry) {
        this.brokerServiceRegistry = brokerServiceRegistry;
    }

    @Override
    public BooleanResultMessages submitContracts(List<Contract> brokerContracts) {
        List<BooleanResultMessages> listOfResultMessages = new ArrayList<>();

        List<emSeco.custodianUnit.core.entities.contract.Contract> custodianContracts =
                MapFromBrokerContractToCustodianContract(brokerContracts);

        List<emSeco.custodianUnit.core.entities.contract.Contract> contractsWithSameCustodians =
                custodianContracts.stream().filter(contract ->
                        contract.getBuySide().getRoutingInformation().getCustodianId() ==
                                contract.getSellSide().getRoutingInformation().getCustodianId()).
                        collect(Collectors.toList());

        List<emSeco.custodianUnit.core.entities.contract.Contract> contractsWithDifferentCustodians =
                custodianContracts.stream().filter(contract ->
                        contract.getBuySide().getRoutingInformation().getCustodianId() !=
                                contract.getSellSide().getRoutingInformation().getCustodianId()).
                        collect(Collectors.toList());

        contractsWithSameCustodians.stream().collect(Collectors.
                groupingBy(contract -> contract.getBuySide().getRoutingInformation().getCustodianId()))
                .forEach((custodianId, groupedContracts) -> {
                    ICustodian sharedCustodian = brokerServiceRegistry.getCustodians().stream().
                            filter(custodian -> custodian.getCustodianId() == custodianId)
                            .findAny().orElse(null);

                    if (sharedCustodian != null) {
                        listOfResultMessages.add(sharedCustodian.submitContracts_API(groupedContracts));
                    }
                });

        //Since brokerContracts routing is done by checking custodianIds, we check for retail orders that do not have
        //custodianId. If we do not do so, we face nullPointerException.
        if (contractsWithDifferentCustodians.stream().anyMatch(contract -> contract.getBuySide()
                .getRoutingInformation().getCustodianId() != null)) {

            contractsWithDifferentCustodians.stream().collect(Collectors.
                    groupingBy(contract -> contract.getBuySide().getRoutingInformation().getCustodianId()))
                    .forEach((custodianId, groupedContracts) -> {
                        ICustodian buySideCustodian = brokerServiceRegistry.getCustodians().stream().
                                filter(custodian -> custodian.getCustodianId() == custodianId)
                                .findAny().orElse(null);

                        if (buySideCustodian != null) {
                            listOfResultMessages.add(buySideCustodian.submitContracts_API(groupedContracts));
                        }
                    });
        }

        //Since brokerContracts routing is done by checking custodianIds, we check for retail orders that do not have
        //custodianId. If we do not do so, we face nullPointerException.
        if (contractsWithDifferentCustodians.stream().anyMatch(contract -> contract.getSellSide()
                .getRoutingInformation().getCustodianId() != null)) {

            contractsWithDifferentCustodians.stream().collect(Collectors.
                    groupingBy(contract -> contract.getSellSide().getRoutingInformation().getCustodianId()))
                    .forEach((custodianId, groupedContracts) -> {
                        ICustodian sellSideCustodian = brokerServiceRegistry.getCustodians().stream().
                                filter(custodian -> custodian.getCustodianId() == custodianId)
                                .findAny().orElse(null);

                        if (sellSideCustodian != null) {
                            listOfResultMessages.add(sellSideCustodian.submitContracts_API(groupedContracts));
                        }
                    });
        }

        return aggregateListOfResultMessages(listOfResultMessages);
    }

    @Override
    public BooleanResultMessages submitTrades(List<Trade> brokerTrades) {
        List<BooleanResultMessages> listOfResultMessages = new ArrayList<>();

        List<emSeco.custodianUnit.core.entities.trade.Trade> custodianTrades =
                MapFromBrokerTradeToCustodianTrade(brokerTrades);

        List<emSeco.custodianUnit.core.entities.trade.Trade> tradesWithSameCustodian =
                custodianTrades.stream().filter(trade ->
                        trade.getBuySide().getRoutingInformation().getCustodianId() ==
                                trade.getSellSide().getRoutingInformation().getCustodianId()).
                        collect(Collectors.toList());

        List<emSeco.custodianUnit.core.entities.trade.Trade> tradesWithDifferentCustodian =
                custodianTrades.stream().filter(trade ->
                        trade.getBuySide().getRoutingInformation().getCustodianId() !=
                                trade.getSellSide().getRoutingInformation().getCustodianId()).
                        collect(Collectors.toList());

        tradesWithSameCustodian.stream().collect(Collectors.
                groupingBy(trade -> trade.getBuySide().getRoutingInformation().getCustodianId()))
                .forEach((custodianId, groupedTrades) -> {
                    ICustodian sharedCustodian = brokerServiceRegistry.getCustodians().stream().
                            filter(custodian -> custodian.getCustodianId() == custodianId)
                            .findAny().orElse(null);

                    if (sharedCustodian != null) {
                        listOfResultMessages.add(sharedCustodian.submitTrades_API(groupedTrades));
                    }
                });

        //Since contracts routing is done by checking custodianIds, we check for retail orders that do not have
        //custodianId. If we do not do so, we face nullPointerException.
        if (tradesWithDifferentCustodian.stream().anyMatch(contract -> contract.getBuySide()
                .getRoutingInformation().getCustodianId() != null)) {

            tradesWithDifferentCustodian.stream().collect(Collectors.
                    groupingBy(trade -> trade.getBuySide().getRoutingInformation().getCustodianId()))
                    .forEach((custodianId, groupedTrades) -> {
                        ICustodian buySideCustodian = brokerServiceRegistry.getCustodians().stream().
                                filter(custodian -> custodian.getCustodianId() == custodianId)
                                .findAny().orElse(null);

                        if (buySideCustodian != null) {
                            listOfResultMessages.add(buySideCustodian.submitTrades_API(groupedTrades));
                        }
                    });
        }

        //Since contracts routing is done by checking custodianIds, we check for retail orders that do not have
        //custodianId. If we do not do so, we face nullPointerException.
        if (tradesWithDifferentCustodian.stream().anyMatch(contract -> contract.getSellSide()
                .getRoutingInformation().getCustodianId() != null)) {

            tradesWithDifferentCustodian.stream().collect(Collectors.
                    groupingBy(trade -> trade.getSellSide().getRoutingInformation().getCustodianId()))
                    .forEach((custodianId, groupedTrades) -> {
                        ICustodian sellSideCustodian = brokerServiceRegistry.getCustodians().stream().
                                filter(custodian -> custodian.getCustodianId() == custodianId)
                                .findAny().orElse(null);
                        if (sellSideCustodian != null) {
                            listOfResultMessages.add(sellSideCustodian.submitTrades_API(groupedTrades));
                        }
                    });
        }

        return aggregateListOfResultMessages(listOfResultMessages);
    }
}