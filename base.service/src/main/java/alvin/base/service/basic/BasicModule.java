package alvin.base.service.basic;

import alvin.base.service.basic.presenters.BasicPresenter;
import alvin.base.service.basic.services.BasicService;
import alvin.base.service.basic.views.BasicActivity;
import alvin.lib.common.rx.RxManager;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public interface BasicModule {

    @ContributesAndroidInjector(modules = {ViewModule.class})
    BasicActivity basicServiceActivity();

    @ContributesAndroidInjector(modules = {ServiceModule.class})
    BasicService basicService();

    @Module
    interface ViewModule {

        @Binds
        BasicContracts.Presenter bindBasicServiceActivityPresenter(
                BasicPresenter presenter);

        @Binds
        BasicContracts.View bindBasicServiceActivityView(
                BasicActivity activity);
    }

    @Module
    class ServiceModule {

        @Provides
        static RxManager rxManager() {
            return RxManager.newBuilder()
                    .withSubscribeOn(Schedulers::io)
                    .withObserveOn(AndroidSchedulers::mainThread)
                    .build();
        }
    }
}
