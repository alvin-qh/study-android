package alvin.base.dagger;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ApplicationModule.class
})
interface ApplicationComponent extends AndroidInjector<Application> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<Application> { }
}
