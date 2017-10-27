package alvin.net.http.models;

import com.fasterxml.jackson.annotation.JsonProperty;

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
}
