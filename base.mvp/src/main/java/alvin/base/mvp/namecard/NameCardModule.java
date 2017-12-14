package alvin.base.mvp.namecard;

import alvin.base.mvp.namecard.presenter.NameCardAddPresenter;
import alvin.base.mvp.namecard.presenter.NameCardDisplayPresenter;
import alvin.base.mvp.namecard.presenter.NameCardEditPresenter;
import alvin.base.mvp.namecard.views.NameCardAddFragment;
import alvin.base.mvp.namecard.views.NameCardDisplayFragment;
import alvin.base.mvp.namecard.views.NameCardEditDialog;
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

    @ContributesAndroidInjector(modules = {
            ViewModule.class,
            ProviderModule.class
    })
    NameCardAddFragment nameCardAddFragment();

    @ContributesAndroidInjector(modules = {
            ViewModule.class,
            ProviderModule.class
    })
    NameCardEditDialog nameCardEditDialog();

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

        @Binds
        NameCardContracts.EditView editView(NameCardEditDialog dialog);

        @Binds
        NameCardContracts.EditPresenter editPresenter(NameCardEditPresenter presenter);
    }

    @Module
    class ProviderModule {
        @Provides
        RxManager rxManager() {
            return RxManager.newBuilder()
                    .subscribeOn(Schedulers::io)
                    .observeOn(AndroidSchedulers::mainThread)
                    .build();
        }
    }
}