package emSeco.brokerUnit.infrastructure.services.databases.brokerUnitRepositories.implementations;

import com.google.inject.Inject;
import emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces.repositories.*;
import emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces.IBrokerUnitRepositories;

public class BrokerUnitRepositories implements IBrokerUnitRepositories {
    private final IInstitutionalOrdersRepository institutionalOrdersRepository;
    private final IRetailOrdersRepository retailOrdersRepository;
    private final IAllocationDetailRepository allocationDetailRepository;
    private final IContractRepository contractRepository;
    private final ISettlementResultRepository settlementResultRepository;
    private final ITradeRepository tradeRepository;
    private final IBrokerUnitInfoRepository brokerUnitInfoRepository;
    private final IBrokerBankAccountRepository brokerBankAccountRepository;
    private final IBrokerDematAccountRepository brokerDematAccountRepository;
    private final IEquityInformationRepository equityInformationRepository;

    @Inject
    public BrokerUnitRepositories(IInstitutionalOrdersRepository institutionalOrdersRepository,
                                  IRetailOrdersRepository retailOrdersRepository,
                                  IAllocationDetailRepository allocationDetailRepository,
                                  IContractRepository contractRepository,
                                  ISettlementResultRepository settlementResultRepository,
                                  ITradeRepository tradeRepository,
                                  IBrokerUnitInfoRepository brokerUnitInfoRepository,
                                  IBrokerBankAccountRepository brokerBankAccountRepository,
                                  IBrokerDematAccountRepository brokerDematAccountRepository,
                                  IEquityInformationRepository equityInformationRepository) {
        this.institutionalOrdersRepository = institutionalOrdersRepository;
        this.retailOrdersRepository = retailOrdersRepository;
        this.allocationDetailRepository = allocationDetailRepository;
        this.contractRepository = contractRepository;
        this.settlementResultRepository = settlementResultRepository;
        this.tradeRepository = tradeRepository;
        this.brokerUnitInfoRepository = brokerUnitInfoRepository;
        this.brokerBankAccountRepository = brokerBankAccountRepository;
        this.brokerDematAccountRepository = brokerDematAccountRepository;
        this.equityInformationRepository = equityInformationRepository;
    }

    @Override
    public IContractRepository getContractRepository() {
        return contractRepository;
    }

    @Override
    public IInstitutionalOrdersRepository getInstitutionalOrdersRepository() {
        return institutionalOrdersRepository;
    }

    @Override
    public IRetailOrdersRepository getRetailOrdersRepository() {
        return retailOrdersRepository;
    }

    @Override
    public IAllocationDetailRepository getAllocationDetailRepository() {
        return allocationDetailRepository;
    }

    @Override
    public ISettlementResultRepository getSettlementResultRepository() {
        return settlementResultRepository;
    }

    @Override
    public ITradeRepository getTradeRepository() {
        return tradeRepository;
    }

    @Override
    public IBrokerUnitInfoRepository getBrokerUnitInfoRepository() {
        return  brokerUnitInfoRepository;
    }

    @Override
    public IBrokerBankAccountRepository getBrokerBankAccountRepository() {
        return brokerBankAccountRepository;
    }

    @Override
    public IBrokerDematAccountRepository getBrokerDematAccountRepository() {
        return brokerDematAccountRepository;
    }

    @Override
    public IEquityInformationRepository getEquityInformationRepository() {
        return equityInformationRepository;
    }
}
