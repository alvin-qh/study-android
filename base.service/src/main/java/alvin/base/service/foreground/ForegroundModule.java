package alvin.adv.service.foreground;

import alvin.adv.service.foreground.presenters.ForegroundPresenter;
import alvin.adv.service.foreground.services.ForegroundService;
import alvin.adv.service.foreground.views.ForegroundActivity;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface ForegroundModule {

    @ContributesAndroidInjector(modules = {ViewModule.class})
    ForegroundActivity foregroundActivity();

    @ContributesAndroidInjector(modules = {ServiceModule.class})
    ForegroundService foregroundService();

    @Module(includes = {ViewModule.BindModule.class})
    class ViewModule {

        @Module
        public interface BindModule {

            @Binds
            ForegroundContracts.IView view(ForegroundActivity activity);

            @Binds
            ForegroundContracts.Presenter presenter(ForegroundPresenter presenter);
        }
    }

    @Module
    class ServiceModule {
    }
}
