package emSeco.injectorConfigurations;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import emSeco.clearingbankUnit.core.modules.clearingBank.implementatinos.ClearingBank;
import emSeco.clearingbankUnit.core.modules.clearingBank.interfaces.IClearingBank;
import emSeco.clearingbankUnit.core.services.infrastructureServices.databases.clearingBankRepositories.interfaces.IClearingBankUnitRepositories;
import emSeco.clearingbankUnit.core.services.infrastructureServices.databases.clearingBankRepositories.interfaces.repositories.IBankAccountRepository;
import emSeco.clearingbankUnit.infrastructure.services.databases.clearingBankRepositories.implementations.ClearingBankUnitRepositories;
import emSeco.clearingbankUnit.infrastructure.services.databases.clearingBankRepositories.implementations.repositories.BankAccountRepository;

public class ClearingBankUnitConfigurations extends AbstractModule {
    @Override
    protected void configure() {
        bind(IBankAccountRepository.class).to(BankAccountRepository.class).in(Scopes.SINGLETON);
        bind(IClearingBankUnitRepositories.class).to(ClearingBankUnitRepositories.class).in(Scopes.SINGLETON);


        bind(IClearingBank.class).to(ClearingBank.class).in(Scopes.SINGLETON);
    }
}
