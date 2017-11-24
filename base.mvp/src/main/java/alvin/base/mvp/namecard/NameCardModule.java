package alvin.base.mvp.namecard;

import alvin.base.mvp.namecard.presenter.NameCardAddPresenter;
import alvin.base.mvp.namecard.presenter.NameCardDisplayPresenter;
import alvin.base.mvp.namecard.views.NameCardAddFragment;
import alvin.base.mvp.namecard.views.NameCardDisplayFragment;
import alvin.lib.common.rx.RxManager;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public interface NameCardModule {

    @ContributesAndroidInjector(modules = {
            ViewModule.class,
            ProviderModule.class
    })
    NameCardDisplayFragment nameCardFragment();

    @Module
    interface ViewModule {

        @Binds
        NameCardContracts.DisplayView displayView(NameCardDisplayFragment fragment);

        @Binds
        NameCardContracts.DisplayPresenter displayPresenter(NameCardDisplayPresenter presenter);

        @Binds
        NameCardContracts.AddView addView(NameCardAddFragment fragment);

        @Binds
        NameCardContracts.AddPresenter addPresenter(NameCardAddPresenter presenter);
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
