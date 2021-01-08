package emSeco.injectorConfigurations;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import emSeco.shared.services.dateTimeService.implementations.DateTimeService;
import emSeco.shared.services.dateTimeService.interfaces.IDateTimeService;
import emSeco.shared.services.uuidGenerator.implementations.UUIDGenerator;
import emSeco.shared.services.uuidGenerator.interfaces.IUUIDGenerator;

public class SharedServicesConfigurations extends AbstractModule {

    @Override
    protected void configure() {
        bind(IUUIDGenerator .class).to(UUIDGenerator.class).in(Scopes.SINGLETON);
        bind(IDateTimeService.class).to(DateTimeService.class).in(Scopes.SINGLETON);
    }
}
