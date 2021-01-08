package emSeco.clearingCropUnit.core.services.infrastructureServices.databases.clearingCorpUnitRepositories.interfaces;

import emSeco.clearingCropUnit.core.services.infrastructureServices.databases.clearingCorpUnitRepositories.interfaces.repositories.IClearingCorpUnitInfoRepository;
import emSeco.clearingCropUnit.core.services.infrastructureServices.databases.clearingCorpUnitRepositories.interfaces.repositories.ITradeRepository;
import emSeco.clearingCropUnit.core.services.infrastructureServices.databases.clearingCorpUnitRepositories.interfaces.repositories.ISettlementResultRepository;

public interface IClearingCorpUnitRepositories {
    ITradeRepository getTradeRepository();
    ISettlementResultRepository getSettlementResultRepository();
    IClearingCorpUnitInfoRepository getClearingCorpUnitInfoRepository();
}
