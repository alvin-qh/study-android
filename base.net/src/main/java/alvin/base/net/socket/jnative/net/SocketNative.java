package alvin.adv.net.socket.jnative.net;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import alvin.adv.net.socket.common.config.NetworkConfig;
import alvin.adv.net.socket.common.models.CommandAck;

public class SocketNative implements Closeable, AutoCloseable {
    private static final int TIMEOUT = 1000 * 5;

    private final Socket socket;

    public SocketNative() throws IOException {
        NetworkConfig config = new NetworkConfig();

        this.socket = new Socket();
        this.socket.connect(new InetSocketAddress(config.getHost(), config.getPort()), TIMEOUT);
    }

    public CommandAck getResponse() throws IOException {
        NativeProtocol proto = new NativeProtocol();

        CommandAck ack = proto.unpack(socket.getInputStream());
        if (ack == null) {
            throw new IOException("Invalid ack");
        }

        switch (ack.getCmd()) {
        case "time-ack":
        case "bye-ack":
            break;
        default:
            throw new IOException("Invalid ack command");
        }

        return ack;
    }

    public void getRemoteTime() throws IOException {
        NativeProtocol proto = new NativeProtocol();
        proto.makeTimeCommand(socket.getOutputStream());
    }

    public void disconnect() throws IOException {
        NativeProtocol proto = new NativeProtocol();
        proto.makeDisconnectCommand(socket.getOutputStream());
    }

    public boolean isClosed() {
        return this.socket.isClosed();
    }

    @Override
    public void close() {
        if (!this.socket.isClosed()) {
            try {
                this.socket.close();
            } catch (IOException ignore) {
            }
        }
    }
}
