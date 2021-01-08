package emSeco.injectorConfigurations;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import emSeco.depositoryUnit.core.modules.depository.implementations.Depository;
import emSeco.depositoryUnit.core.modules.depository.interfaces.IDepository;
import emSeco.depositoryUnit.core.services.infrastructureServices.databases.depositoryRepositories.interfaces.IDepositoryUnitRepositories;
import emSeco.depositoryUnit.core.services.infrastructureServices.databases.depositoryRepositories.interfaces.repositories.IDematAccountRepository;
import emSeco.depositoryUnit.infrastructure.services.databases.depositoryRepositories.implementations.DepositoryUnitRepositories;
import emSeco.depositoryUnit.infrastructure.services.databases.depositoryRepositories.implementations.repositories.DematAccountRepository;

public class DepositoryUnitConfigurations extends AbstractModule {
    @Override
    protected void configure() {
        bind(IDematAccountRepository.class).to(DematAccountRepository.class).in(Scopes.SINGLETON);
        bind(IDepositoryUnitRepositories.class).to(DepositoryUnitRepositories.class).in(Scopes.SINGLETON);


        bind(IDepository.class).to(Depository.class).in(Scopes.SINGLETON);
    }
}
