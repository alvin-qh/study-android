package alvin.base.mvp.scope.di;

import javax.inject.Singleton;

import alvin.base.mvp.scope.ScopeContract;
import alvin.base.mvp.scope.views.ScopeMainActivity;
import dagger.Component;

@Singleton
@Component(modules = {ScopeSingletonModule.class})
public interface ScopeSingletonComponent {

    void inject(ScopeMainActivity activity);

    ScopeContract.View view();

    ScopeContract.Presenter presenter();
}
