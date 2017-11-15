package alvin.base.net.http.rx.presenters;

import android.support.annotation.NonNull;

import alvin.base.net.http.common.domain.models.LiveWeather;
import alvin.base.net.http.common.domain.services.WeatherService;
import alvin.lib.common.rx.RxManager;
import alvin.lib.common.rx.SingleSubscriber;
import alvin.lib.mvp.PresenterAdapter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static alvin.base.net.http.WeatherContract.Presenter;
import static alvin.base.net.http.WeatherContract.View;

public class RxPresenter extends PresenterAdapter<View> implements Presenter {
    private static final int RETRY_TIMES = 3;

    private final WeatherService weatherService = new WeatherService();

    private final RxManager rxManager = RxManager.newBuilder()
            .withSubscribeOn(Schedulers::io)
            .withObserveOn(AndroidSchedulers::mainThread)
            .build();

    public RxPresenter(@NonNull View view) {
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
                .config(single -> single.retry(RETRY_TIMES))
                .subscribe(
                        weather -> withView(view -> view.showLiveWeather(weather)),
                        throwable -> withView(view -> view.showDefaultError(throwable))
                );
    }
}
