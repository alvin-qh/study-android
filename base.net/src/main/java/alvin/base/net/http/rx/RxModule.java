package alvin.base.net.http.rx;

import alvin.base.net.http.WeatherContracts;
import alvin.base.net.http.common.domain.services.WeatherService;
import alvin.base.net.http.rx.presenters.RxPresenter;
import alvin.base.net.http.rx.views.RxActivity;
import alvin.lib.common.rx.RxDecorator;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public interface RxModule {

    @ContributesAndroidInjector(modules = {
            ViewModule.class,
            ProvidesModule.class
    })
    RxActivity activity();

    @Module
    interface ViewModule {
        @Binds
        WeatherContracts.View view(final RxActivity activity);

        @Binds
        WeatherContracts.Presenter presenter(final RxPresenter presenter);
    }

    @Module
    class ProvidesModule {

        @Provides
        public static WeatherService weatherService() {
            return new WeatherService();
        }

        @Provides
        public RxDecorator rxDecorator() {
            return RxDecorator.newBuilder()
                    .subscribeOn(Schedulers::io)
                    .observeOn(AndroidSchedulers::mainThread)
                    .build();
        }
    }
}
