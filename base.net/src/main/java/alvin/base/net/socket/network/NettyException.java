package alvin.base.net.socket.network;

public class NettyException extends Exception {
    public NettyException(String message) {
        super(message);
    }

    public NettyException(Throwable cause) {
        super(cause);
    }
}
