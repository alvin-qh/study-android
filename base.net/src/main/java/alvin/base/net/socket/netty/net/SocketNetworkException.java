package alvin.base.net.socket.netty.net;

import android.support.annotation.NonNull;

public class SocketNetworkException extends RuntimeException {

    private final SocketErrorStatus errorStatus;

    SocketNetworkException(@NonNull SocketErrorStatus status) {
        this.errorStatus = status;
    }

    SocketNetworkException(@NonNull SocketErrorStatus errorStatus, String message) {
        super(message);
        this.errorStatus = errorStatus;
    }

    public SocketNetworkException(@NonNull SocketErrorStatus errorStatus, String message, Throwable cause) {
        super(message, cause);
        this.errorStatus = errorStatus;
    }

    public SocketNetworkException(@NonNull SocketErrorStatus errorStatus, Throwable cause) {
        super(cause);
        this.errorStatus = errorStatus;
    }

    public SocketErrorStatus getErrorStatus() {
        return errorStatus;
    }
}
