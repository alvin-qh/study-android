package alvin.net.http.presenters;

import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.function.Consumer;

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

    public WeatherRxPresenter(@NonNull WeatherContract.View view) {
        this.viewRef = new WeakReference<>(view);
    }

    private void withView(Consumer<WeatherContract.View> consumer) {
        WeatherContract.View view = viewRef.get();
        if (view != null) {
            consumer.accept(view);
        }
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
                        weather -> withView(view -> view.showLiveWeather(weather)),
                        throwable -> withView(WeatherContract.View::showWeatherError)
                );
    }
}
