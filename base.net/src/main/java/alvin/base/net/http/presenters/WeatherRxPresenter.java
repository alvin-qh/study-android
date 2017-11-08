package alvin.base.net.http.presenters;

import android.support.annotation.NonNull;

import alvin.base.net.http.models.LiveWeather;
import alvin.base.net.http.services.WeatherService;
import alvin.lib.common.rx.RxManager;
import alvin.lib.common.rx.SingleSubscriber;
import alvin.lib.mvp.PresenterAdapter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static alvin.base.net.http.WeatherContract.Presenter;
import static alvin.base.net.http.WeatherContract.View;

public class WeatherRxPresenter extends PresenterAdapter<View> implements Presenter {

    private final WeatherService weatherService = new WeatherService();

    private final RxManager rxManager = RxManager.newBuilder()
            .withSubscribeOn(Schedulers::io)
            .withObserveOn(AndroidSchedulers::mainThread)
            .build();

    public WeatherRxPresenter(@NonNull View view) {
        super(view);
    }

    @Override
    public void started() {
        super.started();
        getLiveWeather();
    }

    @Override
    public void destroyed() {
        super.destroyed();
        rxManager.clear();
    }

    @Override
    public void getLiveWeather() {
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
                        throwable -> withView(view -> view.showDefaultError(throwable))
                );
    }
}
