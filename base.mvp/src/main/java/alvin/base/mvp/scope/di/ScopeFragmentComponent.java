package alvin.base.mvp.scope.di;

import alvin.base.mvp.scope.ScopeContract;
import alvin.base.mvp.scope.views.ScopeFragment;
import dagger.Component;

@Scopes.Fragment
@Component(
        dependencies = {ScopeActivityComponent.class},
        modules = {ScopeFragmentModule.class})
public interface ScopeFragmentComponent {

    void inject(ScopeFragment fragment);

    ScopeContract.ScopeFragmentView view();

    ScopeContract.ScopeFragmentPresenter presenter();
}
