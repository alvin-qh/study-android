package alvin.adv.net.http.common.domain.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

public class Weather {

    @JsonProperty("cond_code")
    private String conditionCode;

    @JsonProperty("cond_txt")
    private String conditionText;

    @JsonProperty("fl")
    private Integer feelsLikeTemperature;

    @JsonProperty("hum")
    private Integer humidity;

    @JsonProperty("pcpn")
    private Double precipitation;

    @JsonProperty("pres")
    private Integer pressure;

    @JsonProperty("tmp")
    private Integer temperature;

    @JsonProperty("vis")
    private Double visibility;

    @JsonProperty("wind_deg")
    private Integer windDirectionAngle;

    @JsonProperty("wind_dir")
    private String windDirection;

    @JsonProperty("wind_sc")
    private String windPower;

    @JsonProperty("wind_spd")
    private Integer windSpeed;

    @JsonProperty("cloud")
    private Integer cloud;

    Weather() {
    }

    public Weather(String conditionCode, // SUPPRESS
                   String conditionText,
                   Integer feelsLikeTemperature,
                   Integer humidity,
                   Double precipitation,
                   Integer pressure,
                   Integer temperature,
                   Double visibility,
                   Integer windDirectionAngle,
                   String windDirection,
                   String windPower,
                   Integer windSpeed,
                   Integer cloud) {
        this.conditionCode = conditionCode;
        this.conditionText = conditionText;
        this.feelsLikeTemperature = feelsLikeTemperature;
        this.humidity = humidity;
        this.precipitation = precipitation;
        this.pressure = pressure;
        this.temperature = temperature;
        this.visibility = visibility;
        this.windDirectionAngle = windDirectionAngle;
        this.windDirection = windDirection;
        this.windPower = windPower;
        this.windSpeed = windSpeed;
        this.cloud = cloud;
    }

    public String getConditionCode() {
        return conditionCode;
    }

    public String getConditionText() {
        return conditionText;
    }

    public Integer getFeelsLikeTemperature() {
        return feelsLikeTemperature;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public Double getPrecipitation() {
        return precipitation;
    }

    public Integer getPressure() {
        return pressure;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public Double getVisibility() {
        return visibility;
    }

    public Integer getWindDirectionAngle() {
        return windDirectionAngle;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public String getWindPower() {
        return windPower;
    }

    public Integer getWindSpeed() {
        return windSpeed;
    }

    public Integer getCloud() {
        return cloud;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("conditionCode", conditionCode)
                .add("conditionText", conditionText)
                .add("feelsLikeTemperature", feelsLikeTemperature)
                .add("humidity", humidity)
                .add("precipitation", precipitation)
                .add("pressure", pressure)
                .add("temperature", temperature)
                .add("visibility", visibility)
                .add("windDirectionAngle", windDirectionAngle)
                .add("windDirection", windDirection)
                .add("windPower", windPower)
                .add("windSpeed", windSpeed)
                .add("cloud", cloud)
                .toString();
    }
}
