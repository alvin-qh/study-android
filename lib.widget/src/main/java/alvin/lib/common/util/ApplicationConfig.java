package alvin.lib.common.util;

import android.content.Context;

import com.google.common.base.Strings;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationConfig {
    private static ApplicationConfig instance;

    private final Properties properties = new Properties();

    private ApplicationConfig(Context context) throws IOException {
        try (InputStream in = context.getAssets().open("application.properties")) {
            properties.load(in);
        }
    }

    public int getAsInt(String key, int defaultValue) {
        String property = properties.getProperty(key);
        return Strings.isNullOrEmpty(property) ? defaultValue : Integer.parseInt(property);
    }

    public long getAsLong(String key, long defaultValue) {
        String property = properties.getProperty(key);
        return Strings.isNullOrEmpty(property) ? defaultValue : Long.parseLong(property);
    }

    public String get(String key, String defaultValue) {
        String property = properties.getProperty(key);
        return Strings.isNullOrEmpty(property) ? defaultValue : property;
    }

    public String get(String key) {
        return get(key, null);
    }

    public static void initialize(Context context) throws IOException {
        instance = new ApplicationConfig(context);
    }

    public static ApplicationConfig getInstance() {
        if (instance == null) {
            throw new NullPointerException("ApplicationConfig must be initialize first");
        }
        return instance;
    }
}
