package emSeco.brokerUnit.infrastructure.services.databases.brokerUnitRepositories.implementations.repositories;

import emSeco.brokerUnit.core.entities.brokerUnitInfo.BrokerUnitInfo;
import emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces.repositories.IBrokerUnitInfoRepository;

public class BrokerUnitInfoRepository implements IBrokerUnitInfoRepository {
    private BrokerUnitInfo brokerUnitInfo;

    @Override
    public void add(BrokerUnitInfo brokerUnitInfo) {
        this.brokerUnitInfo=brokerUnitInfo;
    }

    @Override
    public BrokerUnitInfo get() {
        return brokerUnitInfo;
    }
}
