package emSeco.clearingCropUnit.core.services.infrastructureServices.databases.clearingCorpUnitRepositories.interfaces.repositories;

import emSeco.clearingCropUnit.core.entities.clearingCorpUnitInfo.ClearingCorpUnitInfo;

public interface IClearingCorpUnitInfoRepository {
    void add(ClearingCorpUnitInfo clearingCorpUnitInfo);
    ClearingCorpUnitInfo get();
}
