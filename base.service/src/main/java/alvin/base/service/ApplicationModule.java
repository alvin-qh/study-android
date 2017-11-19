package alvin.base.service;

import alvin.base.service.lifecycle.LifecycleModule;
import dagger.Module;
import dagger.android.AndroidInjectionModule;

@Module(includes = {
        AndroidInjectionModule.class,
        ApplicationModule.SubModules.class
})
interface ApplicationModule {

    @Module(includes = {
            LifecycleModule.class
    })
    interface SubModules { }
}
