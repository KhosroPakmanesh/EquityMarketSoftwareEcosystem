package emSeco.brokerUnit.infrastructure.services.databases.brokerUnitRepositories.implementations.repositories;

import emSeco.brokerUnit.core.entities.equityInformation.EquityInformation;
import emSeco.brokerUnit.core.services.infrastructureServices.databases.brokerUnitRepositories.interfaces.repositories.IEquityInformationRepository;

import java.util.ArrayList;
import java.util.List;

public class EquityInformationRepository implements IEquityInformationRepository {
    private final List<EquityInformation> equityInformations;

    public EquityInformationRepository() {
        equityInformations = new ArrayList<>();
    }

    @Override
    public void add(EquityInformation equityInformation) {
        if (get(equityInformation.getInstrumentName()) == null){
            this.equityInformations.add(equityInformation);
        }
    }

    @Override
    public EquityInformation get(String instrumentName) {
        return this.equityInformations.stream().
                filter(equityInformation -> equityInformation.getInstrumentName().equals(instrumentName)).
                findAny().orElse(null);
    }
}
