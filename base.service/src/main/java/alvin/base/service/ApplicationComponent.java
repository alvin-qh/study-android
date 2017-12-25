package alvin.base.service;

import javax.inject.Singleton;

import alvin.base.service.bind.BindModule;
import alvin.base.service.foreground.ForegroundModule;
import alvin.base.service.intent.IntentModule;
import alvin.base.service.lifecycle.LifecycleModule;
import alvin.base.service.main.MainModule;
import alvin.base.service.remote.RemoteModule;
import alvin.base.service.working.WorkingModule;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ApplicationModule.class,
        MainModule.class,
        LifecycleModule.class,
        WorkingModule.class,
        BindModule.class,
        IntentModule.class,
        RemoteModule.class,
        ForegroundModule.class
})
public interface ApplicationComponent extends AndroidInjector<Application> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<Application> { }
}
