package alvin.adv.service.bind;

import alvin.adv.service.bind.persenters.BindPresenter;
import alvin.adv.service.bind.services.BindService;
import alvin.adv.service.bind.views.BindActivity;
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
