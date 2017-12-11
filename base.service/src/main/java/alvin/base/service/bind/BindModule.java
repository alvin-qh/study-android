package alvin.base.service.bind;

import alvin.base.service.bind.presenters.BindPresenter;
import alvin.base.service.bind.services.BindService;
import alvin.base.service.bind.views.BindActivity;
import alvin.lib.common.rx.RxManager;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public interface BindModule {

    @ContributesAndroidInjector(modules = {BindModule.ViewModule.class})
    BindActivity bindActivity();

    @ContributesAndroidInjector(modules = {BindModule.ServiceModule.class})
    BindService bindService();

    @Module(includes = {ViewModule._BindModule.class})
    class ViewModule {

        @Module
        public interface _BindModule {  // SUPPRESS
            @Binds
            BindContracts.View view(BindActivity activity);

            @Binds
            BindContracts.Presenter presenter(BindPresenter presenter);
        }
    }

    @Module
    class ServiceModule {

        @Provides
        RxManager rxManager() {
            return RxManager.newBuilder()
                    .subscribeOn(Schedulers::io)
                    .observeOn(AndroidSchedulers::mainThread)
                    .build();
        }
    }
}
