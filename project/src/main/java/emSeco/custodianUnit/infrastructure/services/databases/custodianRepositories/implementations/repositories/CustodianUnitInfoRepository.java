package emSeco.custodianUnit.infrastructure.services.databases.custodianRepositories.implementations.repositories;

import emSeco.custodianUnit.core.entities.custodianUnitInfo.CustodianUnitInfo;
import emSeco.custodianUnit.core.services.infrastructureServices.databases.custodianUnitRepositories.interfaces.repositories.ICustodianUnitInfoRepository;

public class CustodianUnitInfoRepository implements ICustodianUnitInfoRepository {
    private CustodianUnitInfo custodianUnitInfo;

    @Override
    public CustodianUnitInfo get() {
        return custodianUnitInfo;
    }

    @Override
    public void add(CustodianUnitInfo custodianUnitInfo) {
        this.custodianUnitInfo=custodianUnitInfo;
    }
}
