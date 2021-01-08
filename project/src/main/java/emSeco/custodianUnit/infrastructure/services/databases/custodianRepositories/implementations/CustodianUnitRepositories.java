package emSeco.custodianUnit.infrastructure.services.databases.custodianRepositories.implementations;

import com.google.inject.Inject;
import emSeco.custodianUnit.core.services.infrastructureServices.databases.custodianUnitRepositories.interfaces.*;
import emSeco.custodianUnit.core.services.infrastructureServices.databases.custodianUnitRepositories.interfaces.repositories.*;

public class CustodianUnitRepositories implements ICustodianUnitRepositories {
    private final IContractRepository contractRepository;
    private final IAllocationDetailRepository allocationDetailRepository;
    private final ITradeRepository tradeRepository;
    private final ICustodianUnitInfoRepository custodianUnitInfoRepository;
    private final ISettlementResultRepository settlementResultRepository;
    private final ICustodianBankAccountRepository custodianBankAccountRepository;
    private final ICustodianDematAccountRepository custodianDematAccountRepository;

    @Inject
    public CustodianUnitRepositories(IContractRepository contractRepository,
                                     IAllocationDetailRepository allocationDetailRepository,
                                     ITradeRepository tradeRepository,
                                     ICustodianUnitInfoRepository custodianUnitInfoRepository,
                                     ISettlementResultRepository settlementResultRepository,
                                     ICustodianBankAccountRepository custodianBankAccountRepository,
                                     ICustodianDematAccountRepository custodianDematAccountRepository) {
        this.contractRepository = contractRepository;
        this.allocationDetailRepository = allocationDetailRepository;
        this.tradeRepository = tradeRepository;
        this.custodianUnitInfoRepository = custodianUnitInfoRepository;
        this.settlementResultRepository = settlementResultRepository;
        this.custodianBankAccountRepository = custodianBankAccountRepository;
        this.custodianDematAccountRepository = custodianDematAccountRepository;
    }

    @Override
    public IContractRepository getContractRepository() {
        return contractRepository;
    }


    @Override
    public IAllocationDetailRepository getAllocationDetailRepository() {
        return allocationDetailRepository;
    }

    @Override
    public ITradeRepository getTradeRepository() {
        return tradeRepository;
    }

    @Override
    public ICustodianUnitInfoRepository getCustodianUnitInfoRepository() {
        return custodianUnitInfoRepository;
    }

    @Override
    public ISettlementResultRepository getSettlementResultRepository() {
        return settlementResultRepository;
    }

    @Override
    public ICustodianBankAccountRepository getCustodianBankAccountRepository() {
        return custodianBankAccountRepository;
    }

    @Override
    public ICustodianDematAccountRepository getCustodianDematAccountRepository() {
        return custodianDematAccountRepository;
    }
}
