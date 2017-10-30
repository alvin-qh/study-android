package alvin.net.socket.net;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;

import alvin.net.socket.models.CommandAck;

public class NativeSocket implements Closeable, AutoCloseable {
    private final Socket socket;

    public NativeSocket() throws IOException {
        NetworkConfig config = new NetworkConfig();

        this.socket = new Socket(config.getHost(), config.getPort());
    }

    public CommandAck getResponse() throws IOException {
        Protocol proto = new Protocol();

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
        Protocol proto = new Protocol();
        proto.makeTimeCommand(socket.getOutputStream());
    }

    public void disconnect() throws IOException {
        Protocol proto = new Protocol();
        proto.makeDisconnectCommand(socket.getOutputStream());
    }

    public boolean isClosed() {
        return this.socket.isClosed();
    }

    @Override
    public void close() throws IOException {
        if (!this.socket.isClosed()) {
            this.socket.close();
        }
    }
}
