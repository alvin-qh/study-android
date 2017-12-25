package alvin.base.service.bind;

import alvin.base.service.bind.persenters.BindPresenter;
import alvin.base.service.bind.services.BindService;
import alvin.base.service.bind.views.BindActivity;
import alvin.lib.common.rx.RxDecorator;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public interface BindModule {

    @ContributesAndroidInjector(modules = {ViewModule.class})
    BindActivity bindActivity();

    @ContributesAndroidInjector(modules = {ServiceModule.class})
    BindService bindService();

    @Module
    interface ViewModule {
        @Binds
        BindContracts.View view(final BindActivity activity);

        @Binds
        BindContracts.Presenter presenter(final BindPresenter presenter);
    }

    @Module
    class ServiceModule {

        @Provides
        public RxDecorator rxDecorator() {
            return RxDecorator.newBuilder()
                    .subscribeOn(Schedulers::io)
                    .observeOn(AndroidSchedulers::mainThread)
                    .build();
        }
    }
}
