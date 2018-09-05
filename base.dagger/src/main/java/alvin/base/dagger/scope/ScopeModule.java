package alvin.base.dagger.scope;

import alvin.base.dagger.scope.presenters.DependencyPresenter;
import alvin.base.dagger.scope.views.DependencyActivity;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface ScopeModule {

    @ContributesAndroidInjector(modules = {ViewModule.class, FragmentModule.class})
    DependencyActivity mainActivity();

    @Module
    interface ViewModule {
        @Binds
        ScopeContracts.View view(DependencyActivity activity);

        @Binds
        ScopeContracts.Presenter presenter(DependencyPresenter presenter);
    }
}
