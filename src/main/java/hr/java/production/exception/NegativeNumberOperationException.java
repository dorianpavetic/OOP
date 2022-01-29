package hr.java.production.exception;

/**
 * Exception thrown when output of a mathematical operation is negative.
 */
public class NegativeNumberOperationException extends RuntimeException {
    public NegativeNumberOperationException() {
    }

    public NegativeNumberOperationException(String message) {
        super(message);
    }

    public NegativeNumberOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public NegativeNumberOperationException(Throwable cause) {
        super(cause);
    }

    public NegativeNumberOperationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
