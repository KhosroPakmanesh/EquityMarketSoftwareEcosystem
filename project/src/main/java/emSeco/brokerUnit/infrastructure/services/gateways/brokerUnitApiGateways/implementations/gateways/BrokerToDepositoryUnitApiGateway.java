package emSeco.brokerUnit.infrastructure.services.gateways.brokerUnitApiGateways.implementations.gateways;

import com.google.inject.Inject;
import emSeco.brokerUnit.core.services.domainServices.brokerServiceRegistry.interfaces.IBrokerServiceRegistry;
import emSeco.brokerUnit.core.services.infrastructureServices.gateways.brokerUnitApiGateways.interfaces.gateways.IBrokerToDepositoryUnitApiGateway;
import emSeco.depositoryUnit.core.modules.depository.interfaces.IDepository;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;

import emSeco.shared.exceptions.ServiceRegistryMalfunctionException;

import java.util.UUID;

public class BrokerToDepositoryUnitApiGateway implements IBrokerToDepositoryUnitApiGateway {
    private final IBrokerServiceRegistry brokerServiceRegistry;

    @Inject
    public BrokerToDepositoryUnitApiGateway(IBrokerServiceRegistry brokerServiceRegistry) {
        this.brokerServiceRegistry = brokerServiceRegistry;
    }

    @Override
    public BooleanResultMessage debit(UUID depositoryId, UUID dematAccountNumber, String instrumentName, int quantity) {
        IDepository foundDepository= brokerServiceRegistry.getDepository();
        if (foundDepository!= null &&
                foundDepository.getDepositoryId() == depositoryId){
            return foundDepository.debit_API(dematAccountNumber,instrumentName,quantity);
        }
        else {
            throw new ServiceRegistryMalfunctionException
                    ("Broker's service registry malfunctions!");
        }
    }

    @Override
    public BooleanResultMessage credit(UUID depositoryId, UUID dematAccountNumber, String instrumentName, int quantity) {
        IDepository foundDepository= brokerServiceRegistry.getDepository();
        if (foundDepository!= null &&
                foundDepository.getDepositoryId() == depositoryId){
            return foundDepository.credit_API(dematAccountNumber,instrumentName,quantity);
        }
        else {
            throw new ServiceRegistryMalfunctionException
                    ("Broker's service registry malfunctions!");
        }
    }

    @Override
    public BooleanResultMessage checkBalance(UUID depositoryId, UUID dematAccountNumber,
                             String instrumentName, int quantity) {
        IDepository foundDepository= brokerServiceRegistry.getDepository();
        if (foundDepository!= null &&
                foundDepository.getDepositoryId() == depositoryId){
            return foundDepository.checkBalance_API(dematAccountNumber,instrumentName,quantity);
        }
        else {
            throw new ServiceRegistryMalfunctionException
                    ("Broker's service registry malfunctions!");
        }
    }
}
