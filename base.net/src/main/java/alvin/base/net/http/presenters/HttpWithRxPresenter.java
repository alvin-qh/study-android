package alvin.base.net.http.presenters;

import android.annotation.SuppressLint;

import javax.inject.Inject;

import alvin.base.net.http.HttpContracts;
import alvin.base.net.http.domain.models.LiveWeather;
import alvin.base.net.http.domain.services.WeatherService;
import alvin.lib.common.rx.RxDecorator;
import alvin.lib.common.rx.RxType;
import alvin.lib.mvp.contracts.adapters.PresenterAdapter;
import io.reactivex.Single;

public class HttpWithRxPresenter extends PresenterAdapter<HttpContracts.View>
        implements HttpContracts.Presenter {

    private final WeatherService weatherService;
    private final RxDecorator.Builder rxDecoratorBuilder;

    @Inject
    public HttpWithRxPresenter(HttpContracts.View view,
                               WeatherService weatherService,
                               @RxType.IO RxDecorator.Builder rxDecoratorBuilder) {
        super(view);
        this.weatherService = weatherService;
        this.rxDecoratorBuilder = rxDecoratorBuilder;
    }

    @Override
    @SuppressLint("CheckResult")
    public void getLiveWeather() {
        final RxDecorator decorator = rxDecoratorBuilder.build();
        decorator.<LiveWeather>de(
                Single.create(emitter -> {
                    try {
                        LiveWeather weather = weatherService.liveWeather();
                        emitter.onSuccess(weather);
                    } catch (Exception e) {
                        emitter.onError(e);
                    }
                })
        ).subscribe(
                weather -> with(view -> view.showLiveWeather(weather)),
                throwable -> with(view -> view.showError(throwable))
        );
    }
}
