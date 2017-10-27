package alvin.net.http.models;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    private Integer precipitation;

    @JsonProperty("pres")
    private Integer pressure;

    @JsonProperty("tmp")
    private Integer temperature;

    @JsonProperty("vis")
    private Double visibility;

    @JsonProperty("wind_deg")
    private Integer windDirectionAngle;

    @JsonProperty("wind_dir")
    private Integer windDirection;

    @JsonProperty("wind_sc")
    private Integer windPower;

    @JsonProperty("wind_spd")
    private Integer windSpeed;

    Weather() {
    }

    public Weather(String conditionCode, String conditionText, Integer feelsLikeTemperature,
                   Integer humidity, Integer precipitation, Integer pressure, Integer temperature,
                   Double visibility, Integer windDirectionAngle, Integer windDirection,
                   Integer windPower, Integer windSpeed) {
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

    public Integer getPrecipitation() {
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

    public Integer getWindDirection() {
        return windDirection;
    }

    public Integer getWindPower() {
        return windPower;
    }

    public Integer getWindSpeed() {
        return windSpeed;
    }
}
