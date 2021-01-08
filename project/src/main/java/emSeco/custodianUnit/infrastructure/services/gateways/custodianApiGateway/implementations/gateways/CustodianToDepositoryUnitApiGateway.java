package emSeco.custodianUnit.infrastructure.services.gateways.custodianApiGateway.implementations.gateways;


import com.google.inject.Inject;
import emSeco.custodianUnit.core.services.domainServices.custodianServiceRegistry.interfaces.ICustodianServiceRegistry;
import emSeco.custodianUnit.core.services.infrastructureServices.gateways.custodianUnitApiGateway.interfaces.gateways.ICustodianToDepositoryUnitApiGateway;
import emSeco.depositoryUnit.core.modules.depository.interfaces.IDepository;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;

import emSeco.shared.exceptions.ServiceRegistryMalfunctionException;

import java.util.UUID;

public class CustodianToDepositoryUnitApiGateway implements ICustodianToDepositoryUnitApiGateway {
    private final ICustodianServiceRegistry custodianServiceRegistry;

    @Inject
    public CustodianToDepositoryUnitApiGateway(ICustodianServiceRegistry custodianServiceRegistry) {
        this.custodianServiceRegistry = custodianServiceRegistry;
    }

    @Override
    public BooleanResultMessage debit(UUID depositoryId, UUID dematAccountNumber, String instrumentName, int quantity) {
        IDepository foundDepository= custodianServiceRegistry.getDepository();
        if (foundDepository!= null &&
                foundDepository.getDepositoryId() == depositoryId){
            return foundDepository.debit_API(dematAccountNumber,instrumentName,quantity);
        }
        else {
            throw new ServiceRegistryMalfunctionException
                    ("Custodian's service registry malfunctions!");
        }
    }

    @Override
    public BooleanResultMessage credit(UUID depositoryId, UUID dematAccountNumber, String instrumentName, int quantity) {
        IDepository foundDepository= custodianServiceRegistry.getDepository();
        if (foundDepository!= null &&
                foundDepository.getDepositoryId() == depositoryId){
            return foundDepository.credit_API(dematAccountNumber,instrumentName,quantity);
        }
        else {
            throw new ServiceRegistryMalfunctionException
                    ("Custodian's service registry malfunctions!");
        }
    }

    public BooleanResultMessage checkBalance(UUID depositoryId, UUID dematAccountNumber,
                                               String instrumentName, int quantity) {
        IDepository foundDepository= custodianServiceRegistry.getDepository();
        if (foundDepository!= null &&
                foundDepository.getDepositoryId() == depositoryId){
            return foundDepository.checkBalance_API(dematAccountNumber,instrumentName,quantity);
        }
        else {
            throw new ServiceRegistryMalfunctionException
                    ("Custodian's service registry malfunctions!");
        }
    }
}