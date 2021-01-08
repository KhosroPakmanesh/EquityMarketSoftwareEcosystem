package emSeco.custodianUnit.infrastructure.services.gateways.custodianApiGateway.implementations.gateways;


import com.google.inject.Inject;
import emSeco.custodianUnit.core.entities.trade.Trade;
import emSeco.custodianUnit.core.entities.custodianUnitInfo.CustodianUnitInfo;
import emSeco.custodianUnit.core.services.domainServices.custodianServiceRegistry.interfaces.ICustodianServiceRegistry;
import emSeco.custodianUnit.core.services.infrastructureServices.databases.custodianUnitRepositories.interfaces.ICustodianUnitRepositories;
import emSeco.custodianUnit.core.services.infrastructureServices.gateways.custodianUnitApiGateway.interfaces.gateways.ICustodianToClearingCorpUnitApiGateway;
import emSeco.shared.architecturalConstructs.BooleanResultMessages;
import emSeco.shared.exceptions.DataPersistenceMalfunctionException;
import emSeco.shared.exceptions.ServiceRegistryMalfunctionException;

import java.util.List;


import static emSeco.custodianUnit.core.helpers.CustodianToClearingCorpEntitiesMapper.mapCustodianTradesToClearingCorpTrades;

public class CustodianToClearingCorpUnitApiGateway implements ICustodianToClearingCorpUnitApiGateway {
    private final ICustodianServiceRegistry custodianServiceRegistry;
    private final ICustodianUnitRepositories custodianUnitRepositories;

    @Inject
    public CustodianToClearingCorpUnitApiGateway(ICustodianUnitRepositories custodianUnitRepositories,
                                                 ICustodianServiceRegistry custodianServiceRegistry) {
        this.custodianUnitRepositories = custodianUnitRepositories;
        this.custodianServiceRegistry = custodianServiceRegistry;
    }

    @Override
    public List<BooleanResultMessages> submitInstitutionalTrades(List<Trade> trades) {
        CustodianUnitInfo custodianUnitInfo=
                custodianUnitRepositories.getCustodianUnitInfoRepository().get();

        if (custodianUnitInfo == null){
            throw new DataPersistenceMalfunctionException
                    ("Custodian's data persistence mechanism has not stored the data!");
        }

        if (custodianServiceRegistry.getClearingCorp() == null)
        {
            throw new ServiceRegistryMalfunctionException
                    ("Custodian's service registry malfunctions!");
        }

        return custodianServiceRegistry.getClearingCorp().
                submitInstitutionalTrades_API(mapCustodianTradesToClearingCorpTrades(trades,custodianUnitInfo));
    }
}