package alvin.adv.net.http.rx.presenters;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import alvin.adv.net.http.WeatherContracts;
import alvin.adv.net.http.common.domain.models.LiveWeather;
import alvin.adv.net.http.common.domain.services.WeatherService;
import alvin.lib.common.rx.RxDecorator;
import alvin.lib.mvp.contracts.adapters.PresenterAdapter;
import io.reactivex.Single;

public class RxPresenter
        extends PresenterAdapter<WeatherContracts.View>
        implements WeatherContracts.Presenter {

    private static final int RETRY_TIMES = 3;

    private final WeatherService weatherService;
    private final RxDecorator rxDecorator;

    @Inject
    public RxPresenter(@NonNull final WeatherContracts.View view,
                       @NonNull final WeatherService weatherService,
                       @NonNull final RxDecorator rxDecorator) {
        super(view);
        this.weatherService = weatherService;
        this.rxDecorator = rxDecorator;
    }

    @Override
    public void getLiveWeather() {
        rxDecorator.<LiveWeather>de(
                Single.create(emitter -> {
                    try {
                        LiveWeather weather = weatherService.liveWeather();
                        emitter.onSuccess(weather);
                    } catch (Exception e) {
                        emitter.onError(e);
                    }
                })
        )
                .retry(RETRY_TIMES)
                .subscribe(
                        weather -> with(view -> view.showLiveWeather(weather)),
                        throwable -> with(view -> view.showError(throwable))
                );
    }
}
