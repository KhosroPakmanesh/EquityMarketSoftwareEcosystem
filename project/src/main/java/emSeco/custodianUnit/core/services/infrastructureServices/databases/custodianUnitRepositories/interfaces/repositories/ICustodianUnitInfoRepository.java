package emSeco.custodianUnit.core.services.infrastructureServices.databases.custodianUnitRepositories.interfaces.repositories;

import emSeco.custodianUnit.core.entities.custodianUnitInfo.CustodianUnitInfo;

public interface ICustodianUnitInfoRepository {
    CustodianUnitInfo get();
    void add(CustodianUnitInfo custodianUnitInfo);
}
