package alvin.base.net.http.common.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Joiner;

import java.util.ArrayList;
import java.util.List;

import alvin.base.net.R;
import alvin.base.net.http.WeatherContracts;
import alvin.base.net.http.common.domain.models.LiveWeather;
import alvin.lib.mvp.contracts.adapters.ActivityAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class BaseActivity
        extends ActivityAdapter<WeatherContracts.Presenter>
        implements WeatherContracts.View {

    private static final String TAG = BaseActivity.class.getSimpleName();

    @BindView(R.id.text_weather)
    TextView textWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.http_common_activity_base);

        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getLiveWeather();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @OnClick(R.id.btn_refresh)
    public void onRefreshButtonClick(Button b) {
        presenter.getLiveWeather();
    }

    @Override
    public void showLiveWeather(@NonNull LiveWeather weather) {
        textWeather.setText(formatLiveWeather(weather));
    }

    private String formatLiveWeather(LiveWeather weather) {
        List<String> lines = new ArrayList<>();

        lines.add(String.format("%s: %s (%s)",
                getString(R.string.label_city_name),
                weather.getLocation().getName(),
                weather.getLocation().getCityId()));

        lines.add(String.format("%s: %s",
                getString(R.string.label_timezone),
                weather.getLocation().getTimezone()));

        lines.add(String.format("%s: %s",
                getString(R.string.label_longitude),
                weather.getLocation().getLongitude()));

        lines.add(String.format("%s: %s",
                getString(R.string.label_latitude),
                weather.getLocation().getLatitude()));

        lines.add("");

        lines.add(String.format("%s: %s (%s)",
                getString(R.string.label_condition),
                weather.getWeather().getConditionText(),
                weather.getWeather().getConditionCode()));

        lines.add(String.format("%s: %s%s",
                getString(R.string.label_feels_like_temperature),
                weather.getWeather().getFeelsLikeTemperature(),
                getString(R.string.unit_temperature)));

        lines.add(String.format("%s: %s%s",
                getString(R.string.label_temperature),
                weather.getWeather().getTemperature(),
                getString(R.string.unit_temperature)));

        lines.add(String.format("%s: %s%s",
                getString(R.string.label_precipitation),
                weather.getWeather().getPrecipitation(),
                getString(R.string.unit_mm)));

        lines.add(String.format("%s: %s%s",
                getString(R.string.label_pressure),
                weather.getWeather().getPressure(),
                getString(R.string.unit_pa)));

        lines.add(String.format("%s: %s",
                getString(R.string.label_humidity),
                weather.getWeather().getHumidity()));

        lines.add(String.format("%s: %s",
                getString(R.string.label_wind_direction),
                weather.getWeather().getWindDirection()));

        lines.add(String.format("%s: %s",
                getString(R.string.label_wind_power),
                weather.getWeather().getWindPower()));

        lines.add(String.format("%s: %s%s",
                getString(R.string.label_wind_speed),
                weather.getWeather().getWindSpeed(),
                getString(R.string.unit_speed)));

        lines.add(String.format("%s: %s%s",
                getString(R.string.label_visibility),
                weather.getWeather().getVisibility(),
                getString(R.string.unit_km)));

        lines.add(String.format("%s: %s",
                getString(R.string.label_visibility),
                weather.getWeather().getCloud()));

        lines.add("");

        lines.add(String.format("%s: %s",
                getString(R.string.label_update_time),
                weather.getTimestamp().getUtcTime()));

        return Joiner.on("\n").join(lines);
    }

    @Override
    public void showError(@NonNull Throwable error) {
        Log.e(TAG, "Error caused", error);
        Toast.makeText(this, R.string.error_get_weather, Toast.LENGTH_LONG).show();
    }
}
