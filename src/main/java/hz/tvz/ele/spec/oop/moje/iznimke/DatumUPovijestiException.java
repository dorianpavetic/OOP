package hz.tvz.ele.spec.oop.moje.iznimke;

public class DatumUPovijestiException extends RuntimeException {
    public DatumUPovijestiException() {
    }

    public DatumUPovijestiException(String message) {
        super(message);
    }

    public DatumUPovijestiException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatumUPovijestiException(Throwable cause) {
        super(cause);
    }

    public DatumUPovijestiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
