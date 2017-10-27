package alvin.net.http;

public final class WeatherContract {

    public interface View {
    }

    public interface Presenter {
        void doCreate();

        void doDestroy();

        void showLiveWeather();
    }
}
