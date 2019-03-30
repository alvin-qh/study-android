package alvin.base.net.http.domain.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import alvin.base.net.http.domain.models.LiveWeather;
import alvin.lib.common.utils.ApplicationConfig;
import androidx.annotation.NonNull;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class WeatherService {
    private final ObjectMapper objectMapper;
    private final OkHttpClient.Builder httpClientBuilder;

    private final WeatherConfig config = new WeatherConfig();

    public WeatherService(ObjectMapper objectMapper,
                          OkHttpClient.Builder httpClientBuilder) {
        this.objectMapper = objectMapper;
        this.httpClientBuilder = httpClientBuilder;
    }

    @NonNull
    public LiveWeather liveWeather() throws IOException, WeatherException {
        final OkHttpClient client = httpClientBuilder.build();

        final HttpUrl url = buildUrl();
        final Request request = buildRequest(url);

        return parseResponse(client.newCall(request).execute());
    }

    private LiveWeather parseResponse(Response response) throws WeatherException, IOException {
        final ResponseBody responseBody = response.body();
        if (responseBody == null) {
            throw new IOException("No response");
        }

        final String bodyContent = responseBody.string();
        JsonNode node = objectMapper.readTree(bodyContent);

        node = node.get(config.getVersion()).get(0);
        if (!isStatusOk(node)) {
            throw new WeatherException("Get live weather failed");
        }
        return objectMapper.treeToValue(node, LiveWeather.class);
    }

    private Request buildRequest(HttpUrl url) {
        return new Request.Builder()
                .url(url.url())
//              .addHeader("Author", "alvin")
                .build();
    }

    private HttpUrl buildUrl() throws WeatherException {
        HttpUrl url = HttpUrl.parse(config.getUrl() + "/weather/now");
        if (url == null) {
            throw new WeatherException("Invalid API url");
        }
        return url.newBuilder()
                .addQueryParameter("location", config.getLocation())
                .addQueryParameter("key", config.getKey())
                .addQueryParameter("lang", config.getLang())
                .addQueryParameter("unit", config.getUnit())
                .build();
    }

    private boolean isStatusOk(@NonNull JsonNode node) {
        String result = node.get("status").asText();
        return "ok".equals(result);
    }

    static class WeatherConfig {
        private final String url;
        private final String key;
        private final String location;
        private final String lang;
        private final String unit;
        private final String version;

        WeatherConfig() {
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
}
