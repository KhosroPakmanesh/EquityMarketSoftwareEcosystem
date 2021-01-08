package emSeco.clearingCropUnit.infrastructure.services.databases.clearingCorpUnitRepositories.implementations.repositories;

import emSeco.clearingCropUnit.core.entities.clearingCorpUnitInfo.ClearingCorpUnitInfo;
import emSeco.clearingCropUnit.core.services.infrastructureServices.databases.clearingCorpUnitRepositories.interfaces.repositories.IClearingCorpUnitInfoRepository;

public class ClearingCorpUnitInfoRepository implements IClearingCorpUnitInfoRepository {
    private ClearingCorpUnitInfo clearingCorpUnitInfo;
    public void add(ClearingCorpUnitInfo clearingCorpUnitInfo) {
        this.clearingCorpUnitInfo= clearingCorpUnitInfo;
    }
    public ClearingCorpUnitInfo get()
    {
        return clearingCorpUnitInfo;
    }
}
