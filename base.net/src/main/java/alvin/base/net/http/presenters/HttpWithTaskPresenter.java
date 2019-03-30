package alvin.base.net.http.presenters;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import java.io.IOException;

import javax.inject.Inject;

import alvin.base.net.http.HttpContracts;
import alvin.base.net.http.domain.models.LiveWeather;
import alvin.base.net.http.domain.services.WeatherException;
import alvin.base.net.http.domain.services.WeatherService;
import alvin.lib.mvp.contracts.adapters.PresenterAdapter;
import androidx.annotation.NonNull;

public class HttpWithTaskPresenter extends PresenterAdapter<HttpContracts.View>
        implements HttpContracts.Presenter {

    private final WeatherService weatherService;

    private AsyncTask<?, ?, ?> task;

    @Inject
    public HttpWithTaskPresenter(@NonNull HttpContracts.View view,
                                 @NonNull WeatherService weatherService) {
        super(view);
        this.weatherService = weatherService;
    }

    @Override
    @SuppressLint("StaticFieldLeak")
    public void getLiveWeather() {
        this.task = new AsyncTask<Object, Object, LiveWeather>() {
            private Exception exception;

            @Override
            protected LiveWeather doInBackground(Object... objects) {
                try {
                    return weatherService.liveWeather();
                } catch (IOException | WeatherException e) {
                    exception = e;
                    return null;
                }
            }

            @Override
            protected void onPostExecute(LiveWeather weather) {
                if (weather == null) {
                    if (exception != null) {
                        with(view -> view.showError(exception));
                    }
                    return;
                }
                with(view -> view.showLiveWeather(weather));
            }
        }.execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (task != null) {
            task.cancel(true);
        }
    }
}
