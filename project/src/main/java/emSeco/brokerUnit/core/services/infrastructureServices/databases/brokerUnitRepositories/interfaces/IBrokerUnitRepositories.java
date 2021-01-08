package emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces;


import emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces.repositories.*;

public interface IBrokerUnitRepositories {

    IContractRepository getContractRepository() ;

    IInstitutionalOrdersRepository getInstitutionalOrdersRepository() ;

    IRetailOrdersRepository getRetailOrdersRepository() ;

    IAllocationDetailRepository getAllocationDetailRepository() ;

    ISettlementResultRepository getSettlementResultRepository() ;

    ITradeRepository getTradeRepository();

    IBrokerUnitInfoRepository getBrokerUnitInfoRepository();

    IBrokerBankAccountRepository getBrokerBankAccountRepository();

    IBrokerDematAccountRepository getBrokerDematAccountRepository();

    IEquityInformationRepository getEquityInformationRepository();
}
