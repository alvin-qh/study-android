package alvin.net.http.models;


import alvin.common.util.ApplicationConfig;

public class WeatherConfig {
    private final String url;
    private final String key;
    private final String location;
    private final String lang;
    private final String unit;
    private final String version;

    public WeatherConfig() {
        final ApplicationConfig config = ApplicationConfig.getInstance();

        this.url = config.get("heweather.base_url");
        this.key = config.get("heweather.key");
        this.location = config.get("heweather.location");
        this.version = config.get("heweather.version");
        this.lang = config.get("heweather.lang", "en");
        this.unit = config.get("heweather.unit", "m");
    }

    public String getUrl() {
        return url;
    }

    public String getKey() {
        return key;
    }

    public String getLocation() {
        return location;
    }

    public String getLang() {
        return lang;
    }

    public String getUnit() {
        return unit;
    }

    public String getVersion() {
        return version;
    }
}
