package alvin.base.service;

import javax.inject.Singleton;

import alvin.base.service.bind.BindModule;
import alvin.base.service.foreground.ForegroundModule;
import alvin.base.service.intent.IntentModule;
import alvin.base.service.lifecycle.LifecycleModule;
import alvin.base.service.remote.RemoteModule;
import alvin.base.service.working.WorkingModule;
import alvin.lib.common.utils.Versions;
import dagger.Module;
import dagger.Provides;
import dagger.android.support.AndroidSupportInjectionModule;

@Module(includes = {
        AndroidSupportInjectionModule.class,
        ApplicationModule.SubModules.class,
        ApplicationModule.SingletonProvidersModule.class
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

    @Module
    class SingletonProvidersModule {

        @Provides
        @Singleton
        Versions version() {
            return Versions.VERSIONS_O;
        }
    }
}
