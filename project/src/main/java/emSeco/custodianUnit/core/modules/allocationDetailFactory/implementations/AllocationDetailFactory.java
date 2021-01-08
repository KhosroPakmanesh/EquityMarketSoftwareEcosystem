package emSeco.custodianUnit.core.modules.allocationDetailFactory.implementations;

import com.google.inject.Inject;
import emSeco.custodianUnit.core.entities.allocationDetail.AllocationDetail;
import emSeco.custodianUnit.core.modules.allocationDetailFactory.interfaces.IAllocationDetailFactory;
import emSeco.custodianUnit.core.modules.allocationDetailFactory.models.ConstructAllocationDetailOutputClass;
import emSeco.custodianUnit.core.modules.allocationDetailValidator.interfaces.IAllocationDetailValidator;
import emSeco.custodianUnit.core.modules.custodian.models.SubmitAllocationDetailsInputClass;
import emSeco.custodianUnit.core.services.infrastructureServices.databases.custodianUnitRepositories.interfaces.ICustodianUnitRepositories;
import emSeco.custodianUnit.core.services.infrastructureServices.databases.custodianUnitRepositories.interfaces.repositories.ICustodianUnitInfoRepository;
import emSeco.shared.architecturalConstructs.BooleanResultMessage;
import emSeco.shared.exceptions.DataPersistenceMalfunctionException;


import java.util.List;

import static emSeco.custodianUnit.core.helpers.InputClassToCustodianEntitiesMapper.mapSubmitAllocationDetailsInputClassToAllocationDetails;


public class AllocationDetailFactory implements IAllocationDetailFactory {
    private final IAllocationDetailValidator allocationDetailValidator;
    private final ICustodianUnitRepositories custodianUnitRepositories;

    @Inject
    public AllocationDetailFactory(IAllocationDetailValidator allocationDetailValidator,
                                   ICustodianUnitRepositories custodianUnitRepositories) {
        this.allocationDetailValidator = allocationDetailValidator;
        this.custodianUnitRepositories = custodianUnitRepositories;
    }

    @Override
    public ConstructAllocationDetailOutputClass constructAllocationDetail
            (List<SubmitAllocationDetailsInputClass> inputClasses) {

        ICustodianUnitInfoRepository custodianUnitInfoRepository =
                custodianUnitRepositories.getCustodianUnitInfoRepository();

        if (custodianUnitInfoRepository == null){
            throw new DataPersistenceMalfunctionException
                    ("Custodian's data persistence mechanism has not stored the data!");
        }

        List<AllocationDetail> allocationDetails =
                mapSubmitAllocationDetailsInputClassToAllocationDetails
                        (inputClasses,custodianUnitInfoRepository.get().getCustodianId());

        List<BooleanResultMessage> allocationDetailsValidationResultMessages =
                allocationDetailValidator.validateAllocationDetails(allocationDetails);

        return new ConstructAllocationDetailOutputClass
                (allocationDetailsValidationResultMessages,allocationDetails);
    }
}
