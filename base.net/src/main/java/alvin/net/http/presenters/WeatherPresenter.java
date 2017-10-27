package alvin.net.http.presenters;

import android.os.AsyncTask;

import alvin.net.http.WeatherContract;
import alvin.net.http.services.WeatherService;

public class WeatherPresenter implements WeatherContract.Presenter {

    private final WeatherContract.View view;
    private final WeatherService weatherService = new WeatherService();

    public WeatherPresenter(WeatherContract.View view) {
        this.view = view;
    }

    @Override
    public void doCreate() {
    }

    @Override
    public void doDestroy() {
    }

    @Override
    public void showLiveWeather() {
        AsyncTask task = new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] objects) {
                return null;
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
            }
        }.execute();

        task.
    }
}
