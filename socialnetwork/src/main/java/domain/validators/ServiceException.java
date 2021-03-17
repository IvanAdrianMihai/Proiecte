package domain.validators;

/**
 * Exceptie ce se arunca din service
 */
public class ServiceException extends RuntimeException {
    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }
}
