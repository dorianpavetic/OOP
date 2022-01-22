package hr.java.production.exception;

/**
 * Exception thrown when two categories with same names are entered.
 */
public class MultipleCategoryNamesException extends RuntimeException {
    /**
     * Creates new exception with message.
     *
     * @param message message about exception and it's cause.
     */
    public MultipleCategoryNamesException(String message) {
        super(message);
    }

    /**
     * Creates new exception with message and throwable cause.
     *
     * @param message message about exception and it's cause.
     * @param cause throwable that caused the exception.
     */
    public MultipleCategoryNamesException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates new exception with throwable cause, without message.
     *
     * @param cause throwable that caused the exception.
     */
    public MultipleCategoryNamesException(Throwable cause) {
        super(cause);
    }
}
