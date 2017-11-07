package alvin.base.net.http;

import android.support.annotation.NonNull;

import alvin.base.net.http.models.LiveWeather;

public final class WeatherContract {

    private WeatherContract() {
    }

    public interface View {
        void showLiveWeather(@NonNull LiveWeather weather);

        void showWeatherError();
    }

    public interface Presenter {
        void doCreate();

        void doDestroy();

        void showLiveWeather();
    }
}
