package alvin.base.net.http.task.presenters;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.lang.ref.WeakReference;

import alvin.base.net.http.WeatherContract;
import alvin.base.net.http.common.domain.models.LiveWeather;
import alvin.base.net.http.common.domain.services.WeatherException;
import alvin.base.net.http.common.domain.services.WeatherService;
import alvin.lib.mvp.adapters.ViewPresenterAdapter;

public class TaskPresenter extends ViewPresenterAdapter<WeatherContract.View>
        implements WeatherContract.Presenter {

    private final WeatherService weatherService = new WeatherService();

    private AsyncTask<?, ?, ?> task;

    public TaskPresenter(@NonNull WeatherContract.View view) {
        super(view);
    }

    @Override
    public void getLiveWeather() {
        task = new WeatherTask(getViewReference(), weatherService);
        task.execute();
    }

    @Override
    public void onStart() {
        super.onStart();
        getLiveWeather();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (task != null) {
            task.cancel(true);
        }
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
                    view.showError(exception);
                } else {
                    view.showLiveWeather(weather);
                }
            }
        }
    }
}
