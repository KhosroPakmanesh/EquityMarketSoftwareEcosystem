package emSeco.shared.exceptions;

public class DataPersistenceMalfunctionException extends RuntimeException{
    public DataPersistenceMalfunctionException(String message) {
        super(message);
    }
}
