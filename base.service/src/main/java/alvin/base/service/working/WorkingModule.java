package alvin.adv.service.working;

import alvin.adv.service.working.presenters.WorkingPresenter;
import alvin.adv.service.working.services.WorkingService;
import alvin.adv.service.working.view.WorkingActivity;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface WorkingModule {

    @ContributesAndroidInjector(modules = {ViewModule.class})
    WorkingActivity workingActivity();

    @ContributesAndroidInjector(modules = {ServiceModule.class})
    WorkingService workingService();

    @Module
    interface ViewModule {
        @Binds
        WorkingContracts.View view(final WorkingActivity activity);

        @Binds
        WorkingContracts.Presenter presenter(final WorkingPresenter presenter);
    }

    @Module
    class ServiceModule {
    }
}
