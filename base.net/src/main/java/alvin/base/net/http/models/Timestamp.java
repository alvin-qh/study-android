package alvin.base.net.http.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

public class Timestamp {

    @JsonProperty("loc")
    private String localTime;

    @JsonProperty("utc")
    private String utcTime;

    Timestamp() {
    }

    public Timestamp(String localTime, String utcTime) {
        this.localTime = localTime;
        this.utcTime = utcTime;
    }

    public String getLocalTime() {
        return localTime;
    }

    public String getUtcTime() {
        return utcTime;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("localTime", localTime)
                .add("utcTime", utcTime)
                .toString();
    }
}
