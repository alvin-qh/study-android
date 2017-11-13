package alvin.base.service.basic;

import alvin.base.service.basic.presenters.BasicPresenter;
import alvin.base.service.basic.services.BasicService;
import alvin.base.service.basic.views.BasicActivity;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface BasicModule {

    @ContributesAndroidInjector(modules = {MainModule.class})
    BasicActivity basicServiceActivity();

    @ContributesAndroidInjector(modules = {MainModule.class})
    BasicService basicService();

    @Module
    interface MainModule {

        @Binds
        BasicContracts.Presenter bindBasicServiceActivityPresenter(
                BasicPresenter presenter);

        @Binds
        BasicContracts.View bindBasicServiceActivityView(
                BasicActivity activity);
    }
}
