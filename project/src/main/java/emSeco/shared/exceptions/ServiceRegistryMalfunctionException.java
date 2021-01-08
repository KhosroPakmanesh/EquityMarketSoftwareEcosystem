package emSeco.shared.exceptions;

public class ServiceRegistryMalfunctionException extends RuntimeException{
    public ServiceRegistryMalfunctionException(String message) {
        super(message);
    }
}
