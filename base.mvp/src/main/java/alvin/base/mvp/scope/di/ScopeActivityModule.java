package alvin.base.mvp.scope.di;

import alvin.base.mvp.scope.ScopeContract;
import alvin.base.mvp.scope.presenters.ScopeActivityPresenter;
import alvin.base.mvp.scope.views.ScopeActivity;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = {ScopeActivityModule.BindModule.class})
public final class ScopeActivityModule {

    private final ScopeActivity activity;

    public ScopeActivityModule(ScopeActivity activity) {
        this.activity = activity;
    }

    @Provides
    @Scopes.Activity
    ScopeContract.ScopeActivityView view() {
        return activity;
    }

    @Module
    public interface BindModule {

        @Binds
        @Scopes.Activity
        ScopeContract.ScopeActivityPresenter presenter(ScopeActivityPresenter presenter);
    }
}
