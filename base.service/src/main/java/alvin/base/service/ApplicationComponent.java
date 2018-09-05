package alvin.adv.service;

import javax.inject.Singleton;

import alvin.adv.service.bind.BindModule;
import alvin.adv.service.foreground.ForegroundModule;
import alvin.adv.service.intent.IntentModule;
import alvin.adv.service.lifecycle.LifecycleModule;
import alvin.adv.service.main.MainModule;
import alvin.adv.service.remote.RemoteModule;
import alvin.adv.service.working.WorkingModule;
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
