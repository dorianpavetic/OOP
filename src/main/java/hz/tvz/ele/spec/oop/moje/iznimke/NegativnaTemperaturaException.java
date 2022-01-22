package hz.tvz.ele.spec.oop.moje.iznimke;

public class NegativnaTemperaturaException extends Exception {
    public NegativnaTemperaturaException() {
    }

    public NegativnaTemperaturaException(String message) {
        super(message);
    }

    public NegativnaTemperaturaException(String message, Throwable cause) {
        super(message, cause);
    }

    public NegativnaTemperaturaException(Throwable cause) {
        super(cause);
    }

    public NegativnaTemperaturaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
