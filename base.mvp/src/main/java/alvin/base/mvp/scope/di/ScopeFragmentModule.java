package alvin.base.mvp.scope.di;

import alvin.base.mvp.scope.ScopeContract;
import alvin.base.mvp.scope.presenters.ScopeFragmentPresenter;
import alvin.base.mvp.scope.views.ScopeFragment;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = {ScopeFragmentModule.BindModule.class})
public class ScopeFragmentModule {

    private final ScopeFragment fragment;

    public ScopeFragmentModule(ScopeFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @Scopes.Fragment
    public ScopeContract.ScopeFragmentView view() {
        return fragment;
    }

    @Module
    public interface BindModule {

        @Binds
        @Scopes.Fragment
        ScopeContract.ScopeFragmentPresenter presenter(ScopeFragmentPresenter presenter);
    }
}
