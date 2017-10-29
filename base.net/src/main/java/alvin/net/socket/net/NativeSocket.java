package alvin.net.socket.net;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;

public class NativeSocket implements Closeable, AutoCloseable {
    private final Socket socket;

    public NativeSocket() throws IOException {
        NetworkConfig config = new NetworkConfig();

        this.socket = new Socket(config.getHost(), config.getPort());
    }

    public LocalDateTime getRemoteTime() throws IOException {
        Protocol proto = new Protocol();
        proto.makeTimeCommand(socket.getOutputStream());

        return proto.unpackTimeCommand(socket.getInputStream());
    }

    public void disconnect() throws IOException {
        Protocol proto = new Protocol();
        proto.makeDisconnectCommand(socket.getOutputStream());

        proto.unpackDisconnectCommand(socket.getInputStream());
    }

    @Override
    public void close() throws IOException {
        if (!this.socket.isClosed()) {
            this.socket.close();
        }
    }
}
