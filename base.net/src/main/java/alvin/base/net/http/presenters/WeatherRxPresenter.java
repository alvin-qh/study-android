package alvin.base.net.http.presenters;

import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.function.Consumer;

import alvin.lib.common.rx.RxManager;
import alvin.lib.common.rx.SingleSubscriber;
import alvin.base.net.http.WeatherContract;
import alvin.base.net.http.models.LiveWeather;
import alvin.base.net.http.services.WeatherService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WeatherRxPresenter implements WeatherContract.Presenter {

    private final WeatherService weatherService = new WeatherService();
    private final WeakReference<WeatherContract.View> viewRef;

    private final RxManager rxManager = RxManager.newBuilder()
            .withSubscribeOn(Schedulers::io)
            .withObserveOn(AndroidSchedulers::mainThread)
            .build();

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
        rxManager.clear();
        viewRef.clear();
    }

    @Override
    public void showLiveWeather() {
        final SingleSubscriber<LiveWeather> subscriber = rxManager.single(
                emitter -> {
                    try {
                        LiveWeather weather = weatherService.liveWeather();
                        emitter.onSuccess(weather);
                    } catch (Exception e) {
                        emitter.onError(e);
                    }
                }
        );

        subscriber
                .config(single -> single.retry(3))
                .subscribe(
                        weather -> withView(view -> view.showLiveWeather(weather)),
                        throwable -> withView(WeatherContract.View::showWeatherError)
                );
    }
}
