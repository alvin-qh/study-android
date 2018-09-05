package alvin.base.dagger.scope;

import alvin.base.dagger.scope.presenters.ActivityPresenter;
import alvin.base.dagger.scope.presenters.FragmentPresenter;
import alvin.base.dagger.scope.views.ScopeActivity;
import alvin.base.dagger.scope.views.ScopeFragment;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface ScopeModule {

    @ContributesAndroidInjector(modules = {ActivityModule.class})
    @Scopes.Activity
    ScopeActivity scopeActivity();

    @Module
    interface ActivityModule {
        @Binds
        ScopeContracts.ActivityView view(ScopeActivity activity);

        @Binds
        ScopeContracts.ActivityPresenter presenter(ActivityPresenter presenter);

        @ContributesAndroidInjector(modules = {FragmentModule.class})
        @Scopes.Fragment
        ScopeFragment scopeFragment();

        @Module
        interface FragmentModule {
            @Binds
            ScopeContracts.FragmentView view(ScopeFragment fragment);

            @Binds
            ScopeContracts.FragmentPresenter presenter(FragmentPresenter presenter);
        }
    }
}
