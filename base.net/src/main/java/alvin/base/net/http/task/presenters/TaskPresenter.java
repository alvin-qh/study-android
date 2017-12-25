package alvin.base.net.http.task.presenters;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.lang.ref.WeakReference;

import javax.inject.Inject;

import alvin.base.net.http.WeatherContracts;
import alvin.base.net.http.common.domain.models.LiveWeather;
import alvin.base.net.http.common.domain.services.WeatherException;
import alvin.base.net.http.common.domain.services.WeatherService;
import alvin.lib.mvp.contracts.adapters.PresenterAdapter;

public class TaskPresenter
        extends PresenterAdapter<WeatherContracts.View>
        implements WeatherContracts.Presenter {

    private final WeatherService weatherService;

    private AsyncTask<?, ?, ?> task;

    @Inject
    public TaskPresenter(@NonNull WeatherContracts.View view,
                         @NonNull WeatherService weatherService) {
        super(view);
        this.weatherService = weatherService;
    }

    @Override
    public void getLiveWeather() {
        task = new WeatherTask(getTargetRef(), weatherService);
        task.execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (task != null) {
            task.cancel(true);
        }
    }

    private static class WeatherTask extends AsyncTask<Object, Object, LiveWeather> {
        private final WeakReference<WeatherContracts.View> viewRef;
        private final WeatherService weatherService;

        private Exception exception;

        WeatherTask(@NonNull final WeakReference<WeatherContracts.View> viewRef,
                    @NonNull final WeatherService weatherService) {
            this.viewRef = viewRef;
            this.weatherService = weatherService;
        }

        @Override
        @Nullable
        protected LiveWeather doInBackground(@Nullable final Object[] objects) {
            try {
                return weatherService.liveWeather();
            } catch (IOException | WeatherException e) {
                this.exception = e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(@NonNull final LiveWeather weather) {
            WeatherContracts.View view = viewRef.get();

            if (view != null) {
                if (exception != null) {
                    view.showError(exception);
                } else {
                    view.showLiveWeather(weather);
                }
            }
        }
    }
}
