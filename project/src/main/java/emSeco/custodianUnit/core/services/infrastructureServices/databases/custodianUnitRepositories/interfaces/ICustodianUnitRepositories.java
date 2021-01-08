package emSeco.custodianUnit.core.services.infrastructureServices.databases.custodianUnitRepositories.interfaces;

import emSeco.custodianUnit.core.services.infrastructureServices.databases.custodianUnitRepositories.interfaces.repositories.*;

public interface ICustodianUnitRepositories {
    IContractRepository getContractRepository();
    IAllocationDetailRepository getAllocationDetailRepository();
    ITradeRepository getTradeRepository();
    ICustodianUnitInfoRepository getCustodianUnitInfoRepository();
    ISettlementResultRepository getSettlementResultRepository();
    ICustodianDematAccountRepository getCustodianDematAccountRepository();
    ICustodianBankAccountRepository getCustodianBankAccountRepository();
}
