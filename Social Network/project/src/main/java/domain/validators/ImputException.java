package domain.validators;

/**
 * Exceptie ce se arunca la inexistenta datelor in repository
 */

public class ImputException extends RuntimeException {
    public ImputException() {
    }

    public ImputException(String message) {
        super(message);
    }
}