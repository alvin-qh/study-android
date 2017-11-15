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
import alvin.lib.mvp.PresenterAdapter;

import static alvin.base.net.http.WeatherContract.Presenter;
import static alvin.base.net.http.WeatherContract.View;

public class TaskPresenter extends PresenterAdapter<View> implements Presenter {

    private final WeatherService weatherService = new WeatherService();

    private AsyncTask<?, ?, ?> task;

    public TaskPresenter(@NonNull View view) {
        super(view);
    }

    @Override
    public void getLiveWeather() {
        task = new WeatherTask(getViewRef(), weatherService).execute();
    }

    @Override
    public void started() {
        super.started();
        getLiveWeather();
    }

    @Override
    public void destroyed() {
        super.destroyed();

        if (task != null) {
            task.cancel(true);
        }
    }

    private static class WeatherTask extends AsyncTask<Void, Void, LiveWeather> {
        private final WeakReference<View> viewRef;
        private final WeatherService weatherService;

        private Exception exception;

        WeatherTask(@NonNull WeakReference<View> viewRef,
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
                    view.showDefaultError(exception);
                } else {
                    view.showLiveWeather(weather);
                }
            }
        }
    }
}
