package alvin.ui.listing

import alvin.ui.listing.domain.DomainModule
import alvin.ui.listing.list.ListModule
import alvin.ui.listing.recycler.RecyclerModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ApplicationModule::class,
    DomainModule::class,
    ListModule::class,
    RecyclerModule::class
])
interface ApplicationComponent : AndroidInjector<Application> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<Application>()
}
