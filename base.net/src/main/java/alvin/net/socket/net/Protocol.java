package alvin.net.socket.net;

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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;

import alvin.net.socket.models.Command;
import alvin.net.socket.models.CommandAck;

public class Protocol {
    private static final int CHECK_SUM_SIZE = 16;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Protocol() {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public void makeTimeCommand(OutputStream out) throws IOException {
        Command cmd = new Command("time");
        try {
            String msg = objectMapper.writeValueAsString(cmd);
            packMessage(out, msg);
        } catch (JsonProcessingException e) {
            throw new IOException("Invalid command");
        }
    }

    public void makeDisconnectCommand(OutputStream out) throws IOException {
        Command cmd = new Command("bye");
        try {
            String msg = objectMapper.writeValueAsString(cmd);
            packMessage(out, msg);
        } catch (JsonProcessingException e) {
            throw new IOException("Invalid command");
        }
    }

    private byte[] calculateChecksum(byte[] data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data);
            return md.digest();
        } catch (NoSuchAlgorithmException ignore) {
            throw new RuntimeException(ignore);
        }
    }

    private void packMessage(OutputStream out, String msg) throws IOException {
        final byte[] msgData = msg.getBytes(Charsets.UTF_8);

        DataOutputStream dos = new DataOutputStream(out);
        dos.writeInt(msgData.length);
        dos.write(calculateChecksum(msgData));
        dos.write(msgData);
        dos.flush();
    }

    private Optional<CommandAck> unpack(InputStream in) throws IOException {
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

        byte[] checksumVerifyData = calculateChecksum(msgData);
        if (!Arrays.equals(checksumData, checksumVerifyData)) {
            return Optional.empty();
        }

        return Optional.ofNullable(
                objectMapper.readValue(new String(msgData, Charsets.UTF_8), CommandAck.class));
    }

    public LocalDateTime unpackTimeCommand(InputStream in) throws IOException {
        Optional<CommandAck> mayAck = unpack(in);
        if (!mayAck.isPresent()) {
            throw new IOException("Invalid response message");
        }
        CommandAck ack = mayAck.get();

        if (!"time-ack".equals(ack.getCmd())) {
            throw new IOException("Invalid response command");
        }
        return LocalDateTime.from(DateTimeFormatter.ISO_DATE_TIME.parse(ack.getValue()));
    }

    public void unpackDisconnectCommand(InputStream in) throws IOException {
        Optional<CommandAck> mayAck = unpack(in);
        if (!mayAck.isPresent()) {
            throw new IOException("Invalid response message");
        }
        CommandAck ack = mayAck.get();

        if (!"bye-ack".equals(ack.getCmd())) {
            throw new IOException("Invalid response command");
        }
    }
}
