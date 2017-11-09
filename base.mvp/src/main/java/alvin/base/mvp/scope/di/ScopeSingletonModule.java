package alvin.base.mvp.scope.di;

import javax.inject.Singleton;

import alvin.base.mvp.scope.ScopeContract;
import alvin.base.mvp.scope.presenters.ScopeMainPresenter;
import alvin.base.mvp.scope.views.ScopeMainActivity;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = {ScopeSingletonModule.BindModule.class})
public final class ScopeSingletonModule {

    private final ScopeMainActivity activity;

    public ScopeSingletonModule(ScopeMainActivity activity) {
        this.activity = activity;
    }

    @Provides
    @Singleton
    ScopeContract.View view() {
        return activity;
    }

    @Module
    public interface BindModule {

        @Binds
        @Singleton
        ScopeContract.Presenter presenter(ScopeMainPresenter presenter);
    }
}
