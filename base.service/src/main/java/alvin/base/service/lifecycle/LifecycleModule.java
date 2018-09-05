package alvin.adv.service.lifecycle;

import alvin.adv.service.lifecycle.presenters.LifecyclePresenter;
import alvin.adv.service.lifecycle.services.LifecycleService;
import alvin.adv.service.lifecycle.views.LifecycleActivity;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface LifecycleModule {

    @ContributesAndroidInjector(modules = {ViewModule.class})
    LifecycleActivity lifecycleActivity();

    @ContributesAndroidInjector(modules = {ServiceModule.class})
    LifecycleService lifecycleService();

    @Module(includes = {ViewModule.BindModule.class})
    class ViewModule {

        @Module
        public interface BindModule {
            @Binds
            LifecycleContracts.Presenter bindBasicServiceActivityPresenter(
                    LifecyclePresenter presenter);

            @Binds
            LifecycleContracts.View bindBasicServiceActivityView(
                    LifecycleActivity activity);
        }
    }

    @Module
    class ServiceModule {
    }
}
