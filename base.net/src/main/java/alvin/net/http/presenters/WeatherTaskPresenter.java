package alvin.net.http.presenters;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.lang.ref.WeakReference;

import alvin.net.http.WeatherContract;
import alvin.net.http.models.LiveWeather;
import alvin.net.http.services.WeatherException;
import alvin.net.http.services.WeatherService;

public class WeatherTaskPresenter implements WeatherContract.Presenter {

    private final WeatherService weatherService = new WeatherService();
    private final WeakReference<WeatherContract.View> viewRef;

    public WeatherTaskPresenter(WeatherContract.View view) {
        this.viewRef = new WeakReference<>(view);
    }

    @Override
    public void doCreate() {
    }

    @Override
    public void doDestroy() {
        viewRef.clear();
    }

    @Override
    public void showLiveWeather() {
        new WeatherTask(viewRef, weatherService).execute();
    }

    private static class WeatherTask extends AsyncTask<Void, Void, LiveWeather> {
        private final WeakReference<WeatherContract.View> viewRef;
        private final WeatherService weatherService;

        private Exception exception;

        WeatherTask(@NonNull WeakReference<WeatherContract.View> viewRef,
                    @NonNull WeatherService weatherService) {
            this.viewRef = viewRef;
            this.weatherService = weatherService;
        }

        @Override
        protected @Nullable
        LiveWeather doInBackground(@Nullable Void[] objects) {
            try {
                return weatherService.liveWeather();
            } catch (IOException | WeatherException e) {
                this.exception = e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(@NonNull LiveWeather weather) {
            WeatherContract.View view = viewRef.get();

            if (view != null) {
                if (exception != null) {
                    view.showWeatherError();
                } else {
                    view.showLiveWeather(weather);
                }
            }
        }
    }
}
