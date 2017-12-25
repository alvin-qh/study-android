package alvin.adv.permission

import alvin.adv.permission.main.MainModule
import alvin.adv.permission.remoteservice.RemoteServiceModule
import alvin.adv.permission.storage.StorageModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ApplicationModule::class,
    MainModule::class,
    StorageModule::class,
    RemoteServiceModule::class
])
interface ApplicationComponent : AndroidInjector<Application> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<Application>()
}
