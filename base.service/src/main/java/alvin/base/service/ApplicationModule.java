package alvin.base.service;

import alvin.base.service.bind.BindModule;
import alvin.base.service.foreground.ForegroundModule;
import alvin.base.service.intent.IntentModule;
import alvin.base.service.lifecycle.LifecycleModule;
import alvin.base.service.remote.RemoteModule;
import alvin.base.service.working.WorkingModule;
import dagger.Module;
import dagger.android.support.AndroidSupportInjectionModule;

@Module(includes = {
        AndroidSupportInjectionModule.class,
        ApplicationModule.SubModules.class
})
interface ApplicationModule {

    @Module(includes = {
            LifecycleModule.class,
            WorkingModule.class,
            BindModule.class,
            IntentModule.class,
            RemoteModule.class,
            ForegroundModule.class
    })
    interface SubModules { }
}
