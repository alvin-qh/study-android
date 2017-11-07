package alvin.base.net.http.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("cityId", cityId)
                .add("name", name)
                .add("parentCityName", parentCityName)
                .add("adminAreaName", adminAreaName)
                .add("countryName", countryName)
                .add("longitude", longitude)
                .add("latitude", latitude)
                .add("timezone", timezone)
                .toString();
    }
}
