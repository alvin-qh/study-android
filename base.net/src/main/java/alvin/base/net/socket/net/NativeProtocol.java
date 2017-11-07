package alvin.base.net.socket.net;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Charsets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import alvin.lib.common.util.MD5;
import alvin.base.net.socket.models.Command;
import alvin.base.net.socket.models.CommandAck;

public class NativeProtocol {
    private static final int CHECK_SUM_SIZE = 16;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        OBJECT_MAPPER.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    void makeTimeCommand(OutputStream out) throws IOException {
        Command cmd = new Command("time");
        try {
            String msg = OBJECT_MAPPER.writeValueAsString(cmd);
            packMessage(out, msg);
        } catch (JsonProcessingException e) {
            throw new IOException("Invalid command");
        }
    }

    void makeDisconnectCommand(OutputStream out) throws IOException {
        Command cmd = new Command("bye");
        try {
            String msg = OBJECT_MAPPER.writeValueAsString(cmd);
            packMessage(out, msg);
        } catch (JsonProcessingException e) {
            throw new IOException("Invalid command");
        }
    }

    private void packMessage(OutputStream out, String msg) throws IOException {
        final byte[] msgData = msg.getBytes(Charsets.UTF_8);

        DataOutputStream dos = new DataOutputStream(out);
        dos.writeInt(msgData.length);
        dos.write(new MD5().digest(msgData));
        dos.write(msgData);
        dos.flush();
    }

    public CommandAck unpack(InputStream in) throws IOException {
        DataInputStream din = new DataInputStream(in);
        int length = din.readInt();

        byte[] checksumData = new byte[CHECK_SUM_SIZE];
        int count = din.read(checksumData);
        if (count != CHECK_SUM_SIZE) {
            throw new IOException("Data load failed");
        }

        byte[] msgData = new byte[length];
        count = din.read(msgData);
        if (count != length) {
            throw new IOException("Data load failed");
        }

        byte[] checksumVerifyData = new MD5().digest(msgData);
        if (!Arrays.equals(checksumData, checksumVerifyData)) {
            throw new IOException("Checksum invalid");
        }

        return OBJECT_MAPPER.readValue(new String(msgData, Charsets.UTF_8), CommandAck.class);
    }
}
