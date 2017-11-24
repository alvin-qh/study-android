package alvin.base.mvp.main;

import alvin.base.mvp.main.presenter.MainPresenter;
import alvin.base.mvp.main.views.MainActivity;
import alvin.lib.common.rx.RxManager;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public interface MainModule {

    @ContributesAndroidInjector(modules = {
            ViewModule.class,
            ProviderModule.class
    })
    MainActivity mainActivity();

    @Module
    interface ViewModule {

        @Binds
        MainContracts.View view(MainActivity activity);

        @Binds
        MainContracts.Presenter presenter(MainPresenter presenter);
    }

    @Module
    class ProviderModule {

        @Provides
        RxManager rxManager() {
            return RxManager.newBuilder()
                    .withSubscribeOn(Schedulers::io)
                    .withObserveOn(AndroidSchedulers::mainThread)
                    .build();
        }
    }
}
