package alvin.ui.ime

import alvin.ui.ime.common.CommonModule
import alvin.ui.ime.main.MainModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ApplicationModule::class,
    CommonModule::class,
    MainModule::class
])
interface ApplicationComponent : AndroidInjector<Application> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<Application>()
}
