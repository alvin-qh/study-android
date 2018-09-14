package alvin.base.dagger;

import javax.inject.Singleton;

import alvin.base.dagger.basic.BasicModule;
import alvin.base.dagger.multibindings.MultibindingsModule;
import alvin.base.dagger.scope.ScopeModule;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ApplicationModule.class,
        BasicModule.class,
        ScopeModule.class,
        MultibindingsModule.class
})
interface ApplicationComponent extends AndroidInjector<Application> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<Application> { }
}
