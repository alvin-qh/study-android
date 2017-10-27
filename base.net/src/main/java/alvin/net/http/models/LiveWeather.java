package alvin.net.http.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LiveWeather {

    @JsonProperty("basic")
    private Location location;

    @JsonProperty("now")
    private Weather weather;

    @JsonProperty("update")
    private Timestamp timestamp;

    LiveWeather() {
    }

    public LiveWeather(Location location, Weather weather, Timestamp timestamp) {
        this.location = location;
        this.weather = weather;
        this.timestamp = timestamp;
    }

    public Location getLocation() {
        return location;
    }

    public Weather getWeather() {
        return weather;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
