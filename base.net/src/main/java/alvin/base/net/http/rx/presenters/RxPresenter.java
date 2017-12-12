package alvin.base.net.http.rx.presenters;

import android.support.annotation.NonNull;

import alvin.base.net.http.WeatherContract;
import alvin.base.net.http.common.domain.models.LiveWeather;
import alvin.base.net.http.common.domain.services.WeatherService;
import alvin.lib.common.rx.RxManager;
import alvin.lib.common.rx.SingleSubscriber;
import alvin.lib.mvp.adapters.ViewPresenterAdapter;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxPresenter extends ViewPresenterAdapter<WeatherContract.View>
        implements WeatherContract.Presenter {

    private static final int RETRY_TIMES = 3;

    private final WeatherService weatherService = new WeatherService();

    private final RxManager rxManager = RxManager.newBuilder()
            .subscribeOn(Schedulers::io)
            .observeOn(AndroidSchedulers::mainThread)
            .build();

    public RxPresenter(@NonNull WeatherContract.View view) {
        super(view);
    }

    @Override
    public void onStart() {
        super.onStart();
        getLiveWeather();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        rxManager.clear();
    }

    @Override
    public void getLiveWeather() {
        final SingleSubscriber<LiveWeather> subscriber = rxManager.with(
                Single.<LiveWeather>create(emitter -> {
                    try {
                        LiveWeather weather = weatherService.liveWeather();
                        emitter.onSuccess(weather);
                    } catch (Exception e) {
                        emitter.onError(e);
                    }
                }).retry(RETRY_TIMES)
        );

        subscriber.subscribe(
                weather -> withView(view -> view.showLiveWeather(weather)),
                throwable -> withView(view -> view.showError(throwable))
        );
    }
}
