package emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces.repositories;

import emSeco.brokerUnit.core.entities.equityInformation.EquityInformation;

public interface IEquityInformationRepository {
    void add(EquityInformation equityInformation);
    EquityInformation get(String instrumentName);
}
