package alvin.common.exceptions;

public class ExceptionWrapper extends RuntimeException {

    public ExceptionWrapper(String message, Throwable cause) {
        super(message, cause);
    }

    public ExceptionWrapper(Throwable cause) {
        super(cause);
    }
}
