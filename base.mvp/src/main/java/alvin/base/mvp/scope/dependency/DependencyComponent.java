package alvin.base.mvp.scope.dependency;

import alvin.base.mvp.scope.Scopes;
import alvin.base.mvp.scope.dependency.views.DependencyActivity;
import alvin.base.mvp.scope.domain.service.ActivityScopeService;
import dagger.Component;

@Scopes.Activity
@Component(modules = {DependencyModule.class})
public interface DependencyComponent {

    void inject(DependencyActivity activity);

    DependencyContracts.View view();

    DependencyContracts.Presenter presenter();

    ActivityScopeService activityScopeService();
}
