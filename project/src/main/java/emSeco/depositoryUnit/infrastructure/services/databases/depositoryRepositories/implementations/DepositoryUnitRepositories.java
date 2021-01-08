package emSeco.depositoryUnit.infrastructure.services.databases.depositoryRepositories.implementations;

import com.google.inject.Inject;
import emSeco.depositoryUnit.core.services.infrastructureServices.databases.depositoryRepositories.interfaces.IDepositoryUnitRepositories;
import emSeco.depositoryUnit.core.services.infrastructureServices.databases.depositoryRepositories.interfaces.repositories.IDematAccountRepository;

public class DepositoryUnitRepositories implements IDepositoryUnitRepositories {

    private final IDematAccountRepository dematAccountRepository;

    @Inject
    public DepositoryUnitRepositories(IDematAccountRepository dematAccountRepository) {
        this.dematAccountRepository = dematAccountRepository;
    }

    @Override
    public IDematAccountRepository getDematAccountRepository() {
        return dematAccountRepository;
    }
}
