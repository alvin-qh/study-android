package alvin.base.mvp.scope.di;

import alvin.base.mvp.scope.ScopeContract;
import alvin.base.mvp.scope.domain.service.ScopeActivityService;
import alvin.base.mvp.scope.views.ScopeActivity;
import dagger.Component;

@Scopes.Activity
@Component(modules = {ScopeActivityModule.class})
public interface ScopeActivityComponent {

    void inject(ScopeActivity activity);

    ScopeContract.ScopeActivityView view();

    ScopeContract.ScopeActivityPresenter presenter();

    ScopeActivityService singletonService();
}
