package emSeco.clearingCropUnit.infrastructure.services.gateways.clearingCorpApiGateways.implementations.gateways;

import com.google.inject.Inject;
import emSeco.clearingCropUnit.core.services.domainServices.clearingCorpServiceRegistry.interfaces.IClearingCorpServiceRegistry;
import emSeco.clearingCropUnit.core.services.infrastructureServices.gateways.clearingCorpApiGateway.interfaces.gateways.IClearingCorpToDepositoryUnitApiGateway;
import emSeco.depositoryUnit.core.modules.depository.interfaces.IDepository;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.exceptions.ServiceRegistryMalfunctionException;

import java.util.UUID;

public class ClearingCorpToDepositoryUnitApiGateway implements IClearingCorpToDepositoryUnitApiGateway {
    private final IClearingCorpServiceRegistry clearingCorpServiceRegistry;

    @Inject
    public ClearingCorpToDepositoryUnitApiGateway(IClearingCorpServiceRegistry clearingCorpServiceRegistry) {
        this.clearingCorpServiceRegistry = clearingCorpServiceRegistry;
    }


    public BooleanResultMessage debit(UUID depositoryId, UUID dematAccountNumber, String instrumentName, int quantity) {
        IDepository foundDepository= clearingCorpServiceRegistry.getDepository();
        if (foundDepository!= null &&
                foundDepository.getDepositoryId() == depositoryId){
            return foundDepository.debit_API(dematAccountNumber,instrumentName,quantity);
        }
        else {
            throw new ServiceRegistryMalfunctionException
                    ("Clearing Corporation's service registry malfunctions!");
        }
    }

    @Override
    public BooleanResultMessage credit(UUID depositoryId, UUID dematAccountNumber, String instrumentName, int quantity) {
        IDepository foundDepository= clearingCorpServiceRegistry.getDepository();
        if (foundDepository!= null &&
                foundDepository.getDepositoryId() == depositoryId){
            return foundDepository.credit_API(dematAccountNumber,instrumentName,quantity);
        }
        else {
            throw new ServiceRegistryMalfunctionException
                    ("Clearing Corporation's service registry malfunctions!");
        }
    }

    @Override
    public BooleanResultMessage checkBalance(UUID depositoryId, UUID dematAccountNumber,
                                               String instrumentName, int quantity) {
        IDepository foundDepository= clearingCorpServiceRegistry.getDepository();
        if (foundDepository!= null &&
                foundDepository.getDepositoryId() == depositoryId){
            return foundDepository.checkBalance_API(dematAccountNumber,instrumentName,quantity);
        }
        else {
            throw new ServiceRegistryMalfunctionException
                    ("Clearing Corporation's service registry malfunctions!");
        }
    }
}
