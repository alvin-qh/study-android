package alvin.base.dagger.scope;

import alvin.base.dagger.scope.fragment.presenters.FragmentPresenter;
import alvin.base.dagger.scope.fragment.views.DependencyFragment;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
@Scopes.Fragment
public interface FragmentModule {

    @ContributesAndroidInjector(modules = {ViewModule.class})
    DependencyFragment dependencyFragment();

    @Module
    interface ViewModule {
        @Binds
        Contracts.View view(DependencyFragment fragment);

        @Binds
        Contracts.Presenter presenter(FragmentPresenter presenter);
    }
}
