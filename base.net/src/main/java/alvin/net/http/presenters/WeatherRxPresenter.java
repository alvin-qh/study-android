package alvin.net.http.presenters;

import java.lang.ref.WeakReference;
import java.util.Optional;

import alvin.net.http.WeatherContract;
import alvin.net.http.models.LiveWeather;
import alvin.net.http.services.WeatherService;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WeatherRxPresenter implements WeatherContract.Presenter {

    private final WeatherService weatherService = new WeatherService();
    private final WeakReference<WeatherContract.View> viewRef;

    private Disposable weatherSubscribe;

    public WeatherRxPresenter(WeatherContract.View view) {
        this.viewRef = new WeakReference<>(view);
    }

    @Override
    public void doCreate() {
    }

    @Override
    public void doDestroy() {
        if (weatherSubscribe != null && !weatherSubscribe.isDisposed()) {
            weatherSubscribe.dispose();
        }
        viewRef.clear();
    }

    @Override
    public void showLiveWeather() {
        weatherSubscribe = Single.<LiveWeather>create(emitter -> {
            try {
                LiveWeather weather = weatherService.liveWeather();
                emitter.onSuccess(weather);
            } catch (Exception e) {
                emitter.onError(e);
            }
        })
                .retry(3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        weather ->
                                Optional.ofNullable(viewRef.get())
                                        .ifPresent(view -> view.showLiveWeather(weather)),
                        throwable ->
                                Optional.ofNullable(viewRef.get())
                                        .ifPresent(WeatherContract.View::showWeatherError)
                );
    }
}
