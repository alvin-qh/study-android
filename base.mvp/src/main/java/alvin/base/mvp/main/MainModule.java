package alvin.adv.mvp.main;

import alvin.adv.mvp.main.presenter.MainPresenter;
import alvin.adv.mvp.main.views.MainActivity;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

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
    }
}
