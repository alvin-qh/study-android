package alvin.net.http.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Location {
    @JsonProperty("cid")
    private String cityId;

    @JsonProperty("location")
    private String name;

    @JsonProperty("parent_city")
    private String parentCityName;

    @JsonProperty("admin_area")
    private String adminAreaName;

    @JsonProperty("cnty")
    private String countryName;

    @JsonProperty("lon")
    private Double longitude;

    @JsonProperty("lat")
    private Double latitude;

    @JsonProperty("tz")
    private String timezone;

    Location() {
    }

    public Location(String cityId, String name, String parentCityName, String adminAreaName,
                    String countryName, Double longitude, Double latitude, String timezone) {
        this.cityId = cityId;
        this.name = name;
        this.parentCityName = parentCityName;
        this.adminAreaName = adminAreaName;
        this.countryName = countryName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.timezone = timezone;
    }

    public String getCityId() {
        return cityId;
    }

    public String getName() {
        return name;
    }

    public String getParentCityName() {
        return parentCityName;
    }

    public String getAdminAreaName() {
        return adminAreaName;
    }

    public String getCountryName() {
        return countryName;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public String getTimezone() {
        return timezone;
    }
}
