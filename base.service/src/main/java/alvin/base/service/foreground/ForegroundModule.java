package alvin.base.service.foreground;

import alvin.base.service.foreground.presenters.ForegroundPresenter;
import alvin.base.service.foreground.services.ForegroundService;
import alvin.base.service.foreground.views.ForegroundActivity;
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
            ForegroundContracts.View view(ForegroundActivity activity);

            @Binds
            ForegroundContracts.Presenter presenter(ForegroundPresenter presenter);
        }
    }

    @Module
    class ServiceModule {

    }
}