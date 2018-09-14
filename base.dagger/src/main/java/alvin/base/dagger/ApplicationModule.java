package alvin.base.dagger;

import alvin.base.dagger.basic.BasicModule;
import alvin.base.dagger.basic.views.BasicActivity;
import alvin.base.dagger.multibindings.MultibindingsModule;
import alvin.base.dagger.multibindings.views.MultibindingsActivity;
import alvin.base.dagger.scope.ScopeModule;
import alvin.base.dagger.scope.Scopes;
import alvin.base.dagger.scope.views.ScopeActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
interface ApplicationModule {

    @ContributesAndroidInjector(modules = BasicModule.class)
    BasicActivity basicActivity();

    @ContributesAndroidInjector(modules = ScopeModule.class)
    @Scopes.Activity
    ScopeActivity scopeActivity();

    @ContributesAndroidInjector(modules = MultibindingsModule.class)
    MultibindingsActivity multibindingsActivity();
}
