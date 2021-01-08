package emSeco.clearingCropUnit.infrastructure.services.databases.clearingCorpUnitRepositories.implementations;

import com.google.inject.Inject;
import emSeco.clearingCropUnit.core.services.infrastructureServices.databases.clearingCorpUnitRepositories.interfaces.repositories.IClearingCorpUnitInfoRepository;
import emSeco.clearingCropUnit.core.services.infrastructureServices.databases.clearingCorpUnitRepositories.interfaces.IClearingCorpUnitRepositories;
import emSeco.clearingCropUnit.core.services.infrastructureServices.databases.clearingCorpUnitRepositories.interfaces.repositories.ITradeRepository;
import emSeco.clearingCropUnit.core.services.infrastructureServices.databases.clearingCorpUnitRepositories.interfaces.repositories.ISettlementResultRepository;

public class ClearingCorpUnitRepositories implements IClearingCorpUnitRepositories {
    private final ITradeRepository tradeRepository;
    private final ISettlementResultRepository settlementResultRepository;
    private final IClearingCorpUnitInfoRepository clearingCorpUnitInfoRepository;

    @Inject
    public ClearingCorpUnitRepositories(ITradeRepository tradeRepository,
                                        ISettlementResultRepository settlementResultRepository,
                                        IClearingCorpUnitInfoRepository clearingCorpUnitInfoRepository) {
        this.tradeRepository = tradeRepository;
        this.settlementResultRepository = settlementResultRepository;
        this.clearingCorpUnitInfoRepository = clearingCorpUnitInfoRepository;
    }

    @Override
    public ITradeRepository getTradeRepository() {
        return tradeRepository;
    }

    @Override
    public ISettlementResultRepository getSettlementResultRepository() {
        return settlementResultRepository;
    }

    @Override
    public IClearingCorpUnitInfoRepository getClearingCorpUnitInfoRepository() {
        return clearingCorpUnitInfoRepository;
    }
}
