package alvin.base.net.socket.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import alvin.base.net.socket.common.commands.Command;
import alvin.base.net.socket.common.commands.CommandAck;
import alvin.lib.common.utils.MD5;

public class NativeProtocol {
    private static final int CHECK_SUM_SIZE = 16;

    private final ObjectMapper objectMapper;

    public NativeProtocol(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    void makeTimeCommand(OutputStream out) throws IOException {
        Command cmd = new Command("time");
        try {
            String msg = objectMapper.writeValueAsString(cmd);
            packMessage(out, msg);
        } catch (JsonProcessingException e) {
            throw new IOException("Invalid command");
        }
    }

    void makeDisconnectCommand(OutputStream out) throws IOException {
        Command cmd = new Command("bye");
        try {
            String msg = objectMapper.writeValueAsString(cmd);
            packMessage(out, msg);
        } catch (JsonProcessingException e) {
            throw new IOException("Invalid command");
        }
    }

    @SuppressWarnings("SynchronizationOnLocalVariableOrMethodParameter")
    private void packMessage(OutputStream out, String msg) throws IOException {
        final byte[] msgData = msg.getBytes(Charsets.UTF_8);

        synchronized (out) {
            DataOutputStream dos = new DataOutputStream(out);
            dos.writeInt(msgData.length);
            dos.write(new MD5().digest(msgData));
            dos.write(msgData);
            dos.flush();
        }
    }

    @SuppressWarnings("SynchronizationOnLocalVariableOrMethodParameter")
    public CommandAck unpack(InputStream in) throws IOException {
        byte[] checksumData = new byte[CHECK_SUM_SIZE];
        byte[] msgData;
        synchronized (in) {
            DataInputStream din = new DataInputStream(in);
            int length = din.readInt();

            int count = din.read(checksumData);
            if (count != CHECK_SUM_SIZE) {
                throw new IOException("Data load failed");
            }
            msgData = new byte[length];
            count = din.read(msgData);
            if (count != length) {
                throw new IOException("Data load failed");
            }
        }
        byte[] checksumVerifyData = new MD5().digest(msgData);
        if (!Arrays.equals(checksumData, checksumVerifyData)) {
            throw new IOException("Checksum invalid");
        }
        return objectMapper.readValue(new String(msgData, Charsets.UTF_8), CommandAck.class);
    }
}
