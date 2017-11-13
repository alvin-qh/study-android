package alvin.base.service;

import alvin.base.service.basic.BasicModule;
import alvin.base.service.persists.PersistModule;
import dagger.Module;
import dagger.android.AndroidInjectionModule;

@Module(includes = {
        AndroidInjectionModule.class,
        ApplicationModule.SubModules.class,
        PersistModule.class
})
interface ApplicationModule {

    @Module(includes = {
            BasicModule.class
    })
    interface SubModules { }
}
