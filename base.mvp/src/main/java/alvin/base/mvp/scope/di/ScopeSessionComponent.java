package alvin.base.mvp.scope.di;

import alvin.base.mvp.scope.views.SessionFragment;
import alvin.lib.common.di.scopes.Session;
import dagger.Component;

@Session
@Component(dependencies = {ScopeSingletonComponent.class})
public interface ScopeSessionComponent {
    void inject(SessionFragment fragment);
}
