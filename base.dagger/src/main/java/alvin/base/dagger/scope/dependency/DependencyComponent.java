package alvin.base.dagger.scope.dependency;

import alvin.base.dagger.scope.Scopes;
import alvin.base.dagger.scope.dependency.views.DependencyActivity;
import alvin.base.dagger.scope.domain.service.ActivityScopeService;
import dagger.Component;

@Scopes.Activity
@Component(modules = {DependencyModule.class})
public interface DependencyComponent {

    void inject(DependencyActivity activity);

    DependencyContracts.View view();

    DependencyContracts.Presenter presenter();

    ActivityScopeService activityScopeService();
}
