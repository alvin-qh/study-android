package alvin.base.net.socket.network;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

import alvin.base.net.socket.common.commands.CommandAck;
import alvin.base.net.socket.common.config.NetworkConfig;

@SuppressWarnings("SynchronizeOnNonFinalField")
public class NativeSocket implements Closeable, AutoCloseable {
    private static final int TIMEOUT = 1000 * 5;

    private final NativeProtocol protocol;
    private final NetworkConfig config;
    private final AtomicBoolean closed = new AtomicBoolean(true);

    private Socket socket;

    public NativeSocket(NativeProtocol protocol, NetworkConfig config) {
        this.protocol = protocol;
        this.config = config;
    }

    public synchronized void connect() throws IOException {
        if (closed.compareAndSet(true, false)) {
            this.socket = new Socket();
            this.socket.connect(new InetSocketAddress(config.getHost(), config.getPort()), TIMEOUT);
        }
    }

    public CommandAck getResponse() throws IOException {
        if (closed.get()) {
            return null;
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
        if (closed.get()) {
            return;
        }

        final OutputStream output;
        synchronized (socket) {
            output = socket.getOutputStream();
        }
        protocol.makeTimeCommand(output);
    }

    public void bye() throws IOException {
        if (closed.get()) {
            return;
        }

        final OutputStream output;
        synchronized (socket) {
            output = socket.getOutputStream();
        }
        protocol.makeDisconnectCommand(output);
    }

    public synchronized boolean isClosed() {
        return closed.get();
    }

    public synchronized void reconnect() throws IOException {
        close();
        connect();
    }

    @Override
    public synchronized void close() {
        if (closed.compareAndSet(false, true)) {
            if (!this.socket.isClosed()) {
                try {
                    this.socket.close();
                } catch (IOException ignore) {
                }
            }
        }
    }
}
