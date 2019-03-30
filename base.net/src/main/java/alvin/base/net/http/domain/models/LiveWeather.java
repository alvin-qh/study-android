package alvin.base.net.http.domain.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import alvin.lib.mvp.contracts.IModel;
import androidx.annotation.NonNull;

public class LiveWeather implements IModel {

    @JsonProperty("basic")
    private Location location;

    @JsonProperty("now")
    private Weather weather;

    @JsonProperty("update")
    private Timestamp timestamp;

    LiveWeather() {
    }

    public LiveWeather(@NonNull Location location,
                       @NonNull Weather weather,
                       @NonNull Timestamp timestamp) {
        this.location = location;
        this.weather = weather;
        this.timestamp = timestamp;
    }

    public @NonNull
    Location getLocation() {
        return location;
    }

    public @NonNull
    Weather getWeather() {
        return weather;
    }

    public @NonNull
    Timestamp getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("location", location)
                .add("weather", weather)
                .add("timestamp", timestamp)
                .toString();
    }
}
