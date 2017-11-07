package alvin.common.util;

import android.support.annotation.NonNull;

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Hex;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import alvin.common.exceptions.Throwables;


public class MD5 {

    private final MessageDigest digest = createMD5Digest();

    public void update(@NonNull byte[] data) {
        digest.update(data);
    }

    public void update(@NonNull Charset charset,
                       @NonNull String s) {
        update(s.getBytes(charset));
    }

    public void update(@NonNull String s) {
        update(s.getBytes(Charsets.UTF_8));
    }

    @NonNull
    public byte[] digest() {
        return digest.digest();
    }

    @NonNull
    public byte[] digest(@NonNull byte[] data) {
        return digest.digest(data);
    }

    @NonNull
    public byte[] digest(@NonNull String s, @NonNull Charset charset) {
        return digest(s.getBytes(charset));
    }

    @NonNull
    public byte[] digest(@NonNull String s) {
        return digest(s, Charsets.UTF_8);
    }

    @NonNull
    public String digestToString() {
        return new String(Hex.encodeHex(digest()));
    }

    @NonNull
    public String digestToString(boolean toLowerCase) {
        return new String(Hex.encodeHex(digest(), toLowerCase));
    }

    @NonNull
    public String digestToString(@NonNull byte[] data) {
        return new String(Hex.encodeHex(digest(data)));
    }

    @NonNull
    public String digestToString(@NonNull byte[] data, boolean toLowerCase) {
        return new String(Hex.encodeHex(digest(data), toLowerCase));
    }

    @NonNull
    public String digestToString(@NonNull String s) {
        return digestToString(s, Charsets.UTF_8);
    }

    @NonNull
    public String digestToString(@NonNull String s, @NonNull Charset charset) {
        return digestToString(s.getBytes(charset));
    }

    @NonNull
    public String digestToString(@NonNull String s, @NonNull Charset charset, boolean toLowerCase) {
        return digestToString(s.getBytes(charset), toLowerCase);
    }

    @NonNull
    private static MessageDigest createMD5Digest() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw Throwables.propagate(e);
        }
    }
}
