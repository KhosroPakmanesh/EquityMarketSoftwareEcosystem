package emSeco.shared.services.dateTimeService.implementations;

import emSeco.shared.services.dateTimeService.interfaces.IDateTimeService;

import java.util.Date;

public class DateTimeService implements IDateTimeService {
    @Override
    public Date getDateTime() {
        return new Date();
    }
}
