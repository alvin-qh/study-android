package alvin.base.net.socket.network;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import alvin.base.net.socket.common.commands.CommandAck;
import alvin.base.net.socket.common.config.NetworkConfig;

@SuppressWarnings("SynchronizeOnNonFinalField")
public class NativeSocket implements Closeable, AutoCloseable {
    private static final int TIMEOUT = 1000 * 5;

    private final NativeProtocol protocol;
    private final NetworkConfig config;

    private Socket socket;
    private boolean closed = true;

    public NativeSocket(NativeProtocol protocol, NetworkConfig config) {
        this.protocol = protocol;
        this.config = config;
    }

    public synchronized void connect() throws IOException {
        this.socket = new Socket();
        this.socket.connect(new InetSocketAddress(config.getHost(), config.getPort()), TIMEOUT);
        this.closed = false;
    }

    public CommandAck getResponse() throws IOException {
        if (socket == null) {
            throw new IOException("Not connect yet");
        }

        final InputStream input;
        synchronized (socket) {
            input = socket.getInputStream();
        }

        CommandAck ack = protocol.unpack(input);
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
        if (socket == null) {
            throw new IOException("Not connect yet");
        }

        final OutputStream output;
        synchronized (socket) {
            output = socket.getOutputStream();
        }
        protocol.makeTimeCommand(output);
    }

    public void bye() throws IOException {
        if (socket == null) {
            throw new IOException("Not connect yet");
        }

        final OutputStream output;
        synchronized (socket) {
            output = socket.getOutputStream();
        }
        protocol.makeDisconnectCommand(output);
    }

    public synchronized boolean isClosed() {
        return closed;
    }

    public synchronized void reconnect() throws IOException {
        if (!closed) {
            if (!socket.isClosed()) {
                socket.close();
            }
            connect();
        }
    }

    @Override
    public synchronized void close() {
        if (!closed) {
            closed = true;
            if (!this.socket.isClosed()) {
                try {
                    this.socket.close();
                } catch (IOException ignore) {
                }
            }
        }
    }
}
