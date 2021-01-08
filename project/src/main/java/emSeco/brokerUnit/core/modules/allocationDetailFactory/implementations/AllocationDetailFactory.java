package emSeco.brokerUnit.core.modules.allocationDetailFactory.implementations;

import com.google.inject.Inject;
import emSeco.brokerUnit.core.entities.allocationDetail.AllocationDetail;
import emSeco.brokerUnit.core.modules.allocationDetailValidator.interfaces.IAllocationDetailValidator;
import emSeco.brokerUnit.core.modules.broker.models.SubmitAllocationDetailsInputClass;
import emSeco.brokerUnit.core.modules.broker.models.ConstructAllocationDetailOutputClass;
import emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces.IBrokerUnitRepositories;
import emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces.repositories.IBrokerUnitInfoRepository;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.exceptions.DataPersistenceMalfunctionException;


import java.util.List;

import static emSeco.brokerUnit.core.helpers.InputClassToBrokerEntitiesMapper.mapSubmitAllocationDetailsInputClassToAllocationDetails;

public class AllocationDetailFactory implements emSeco.brokerUnit.core.modules.allocationDetailFactory.interfaces.IAllocationDetailFactory {
    private final IAllocationDetailValidator allocationDetailValidator;
    private final IBrokerUnitRepositories brokerUnitRepositories;

    @Inject
    public AllocationDetailFactory(IAllocationDetailValidator allocationDetailValidator,
                                   IBrokerUnitRepositories brokerUnitRepositories) {
        this.allocationDetailValidator = allocationDetailValidator;
        this.brokerUnitRepositories = brokerUnitRepositories;
    }

    @Override
    public ConstructAllocationDetailOutputClass constructAllocationDetail
            (List<SubmitAllocationDetailsInputClass> inputClasses) {
        IBrokerUnitInfoRepository brokerUnitInfoRepository =
                brokerUnitRepositories.getBrokerUnitInfoRepository();

        if (brokerUnitInfoRepository == null)
        {
            throw new DataPersistenceMalfunctionException(
                    "Broker's data persistence mechanism has not stored the data!");
        }

        List<AllocationDetail> allocationDetails =
                mapSubmitAllocationDetailsInputClassToAllocationDetails(
                        inputClasses,brokerUnitInfoRepository.get().getBrokerId());

        List<BooleanResultMessage> allocationDetailsValidationResultMessages =
                allocationDetailValidator.validateAllocationDetails(allocationDetails);

        return new ConstructAllocationDetailOutputClass
                (allocationDetailsValidationResultMessages,allocationDetails);
    }
}