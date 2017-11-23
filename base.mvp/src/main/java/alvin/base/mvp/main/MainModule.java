package alvin.base.mvp.main;

import alvin.base.mvp.main.presenter.MainPresenter;
import alvin.base.mvp.main.views.MainActivity;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface MainModule {

    @ContributesAndroidInjector(modules = {ViewModule.class})
    MainActivity mainActivity();

    @Module
    interface ViewModule {

        @Binds
        MainConstracts.View view(MainActivity activity);

        @Binds
        MainConstracts.Presenter presenter(MainPresenter presenter);
    }
}
