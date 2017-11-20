package alvin.base.service;

import alvin.base.service.bind.BindModule;
import alvin.base.service.lifecycle.LifecycleModule;
import alvin.base.service.working.WorkingModule;
import dagger.Module;
import dagger.android.AndroidInjectionModule;

@Module(includes = {
        AndroidInjectionModule.class,
        ApplicationModule.SubModules.class
})
interface ApplicationModule {

    @Module(includes = {
            LifecycleModule.class,
            WorkingModule.class,
            BindModule.class
    })
    interface SubModules { }
}
