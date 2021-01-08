package emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces.repositories;

import emSeco.brokerUnit.core.entities.brokerUnitInfo.BrokerUnitInfo;

public interface IBrokerUnitInfoRepository {
    void add(BrokerUnitInfo brokerUnitInfo);
    BrokerUnitInfo get();
}
