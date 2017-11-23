package alvin.base.dagger.scope.dependency;

import alvin.base.dagger.scope.Scopes;
import alvin.base.dagger.scope.dependency.presenters.DependencyPresenter;
import alvin.base.dagger.scope.dependency.views.DependencyActivity;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = {DependencyModule.BindModule.class})
public final class DependencyModule {

    private final DependencyActivity activity;

    public DependencyModule(DependencyActivity activity) {
        this.activity = activity;
    }

    @Provides
    @Scopes.Activity
    DependencyContracts.View view() {
        return activity;
    }

    @Module
    public interface BindModule {

        @Binds
        @Scopes.Activity
        DependencyContracts.Presenter presenter(DependencyPresenter presenter);
    }
}
