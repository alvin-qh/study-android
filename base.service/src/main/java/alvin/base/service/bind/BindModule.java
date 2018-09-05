package alvin.base.service.bind;

import alvin.base.service.bind.persenters.BindPresenter;
import alvin.base.service.bind.services.BindService;
import alvin.base.service.bind.views.BindActivity;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

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
    }
}
