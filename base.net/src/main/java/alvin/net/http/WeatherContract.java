package alvin.net.http;

import javax.annotation.Nonnull;

import alvin.net.http.models.LiveWeather;

public final class WeatherContract {

    public interface View {
        void showLiveWeather(@Nonnull LiveWeather weather);

        void showWeatherError();
    }

    public interface Presenter {
        void doCreate();

        void doDestroy();

        void showLiveWeather();
    }
}
