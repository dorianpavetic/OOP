package hr.java.production.exception;

/**
 * Exception thrown when multiple same items are selected during list selection.
 */
public class MultipleInstanceException extends Exception {
    /**
     * Creates new exception with message.
     *
     * @param message message about exception and it's cause.
     */
    public MultipleInstanceException(String message) {
        super(message);
    }

    /**
     * Creates new exception with message and throwable cause.
     *
     * @param message message about exception and it's cause.
     * @param cause throwable that caused the exception.
     */
    public MultipleInstanceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates new exception with throwable cause, without message.
     *
     * @param cause throwable that caused the exception.
     */
    public MultipleInstanceException(Throwable cause) {
        super(cause);
    }
}
