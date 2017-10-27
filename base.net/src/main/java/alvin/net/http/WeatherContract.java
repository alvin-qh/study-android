package alvin.net.http;

import alvin.net.http.models.LiveWeather;

public final class WeatherContract {

    public interface View {
        void showLiveWeather(LiveWeather weather);

        void showWeatherError();
    }

    public interface Presenter {
        void doCreate();

        void doDestroy();

        void showLiveWeather();
    }
}
