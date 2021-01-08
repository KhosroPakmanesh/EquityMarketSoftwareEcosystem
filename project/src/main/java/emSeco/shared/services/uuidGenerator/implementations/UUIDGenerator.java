package emSeco.shared.services.uuidGenerator.implementations;

import emSeco.shared.services.uuidGenerator.interfaces.IUUIDGenerator;

import java.util.UUID;

public class UUIDGenerator implements IUUIDGenerator {
    @Override
    public UUID generateUUID() {
        return UUID.randomUUID();
    }
}
