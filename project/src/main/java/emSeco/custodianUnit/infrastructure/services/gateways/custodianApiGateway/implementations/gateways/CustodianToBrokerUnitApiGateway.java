package emSeco.custodianUnit.infrastructure.services.gateways.custodianApiGateway.implementations.gateways;

import com.google.inject.Inject;
import emSeco.brokerUnit.core.modules.broker.interfaces.IBroker;
import emSeco.custodianUnit.core.entities.contract.Contract;
import emSeco.custodianUnit.core.services.domainServices.custodianServiceRegistry.interfaces.ICustodianServiceRegistry;
import emSeco.custodianUnit.core.services.infrastructureServices.gateways.custodianUnitApiGateway.interfaces.gateways.ICustodianToBrokerUnitApiGateway;
import emSeco.custodianUnit.core.services.infrastructureServices.gateways.custodianUnitApiGateway.interfaces.SendContractAnalysisResultInputClass;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.architecturalConstructs.BooleanResultMessages;
import emSeco.shared.exceptions.ServiceRegistryMalfunctionException;

import java.util.ArrayList;
import java.util.List;

import static emSeco.custodianUnit.core.helpers.CustodianToBrokerEntitiesMapper.mapFromCustodianContractToBrokerContract;
import static emSeco.shared.architecturalConstructs.BooleanResultMessages.aggregateListOfResultMessage;

public class CustodianToBrokerUnitApiGateway implements ICustodianToBrokerUnitApiGateway {
    private final ICustodianServiceRegistry custodianServiceRegistry;

    @Inject
    public CustodianToBrokerUnitApiGateway(ICustodianServiceRegistry custodianServiceRegistry) {
        this.custodianServiceRegistry = custodianServiceRegistry;
    }

    @Override
    public BooleanResultMessages sendContractAnalysisResult(SendContractAnalysisResultInputClass inputClass) {
        List<BooleanResultMessage> resultMessages= new ArrayList<>();

        Contract affirmedContract = inputClass.getAffirmedContracts().stream().findAny().orElse(null);

        if (affirmedContract == null) {
            throw new RuntimeException("There should be at least one affirmed contract!");
        }

        IBroker buySideBroker = custodianServiceRegistry.getBrokers().stream().
                filter(broker -> broker.getBrokerId() ==
                        affirmedContract.getBuySide().getRoutingInformation().getBrokerId()).findAny().orElse(null);

        if (buySideBroker != null) {
            resultMessages.add(
                    buySideBroker.submitContractAnalysisResult_API(inputClass.getOrderId(),
                            mapFromCustodianContractToBrokerContract(inputClass.getAffirmedContracts()),
                            mapFromCustodianContractToBrokerContract(inputClass.getRejectedContracts())));

            return aggregateListOfResultMessage(resultMessages);
        }

        IBroker sellSideBroker = custodianServiceRegistry.getBrokers().stream().
                filter(broker -> broker.getBrokerId() ==
                        affirmedContract.getSellSide().getRoutingInformation().getBrokerId()).findAny().orElse(null);

        if (sellSideBroker != null) {
            resultMessages.add(
                    sellSideBroker.submitContractAnalysisResult_API(inputClass.getOrderId(),
                            mapFromCustodianContractToBrokerContract(inputClass.getAffirmedContracts()),
                            mapFromCustodianContractToBrokerContract(inputClass.getRejectedContracts())));

            return aggregateListOfResultMessage(resultMessages);
        }

        throw new ServiceRegistryMalfunctionException
                ("Custodian's service registry malfunctions!");
    }
}

