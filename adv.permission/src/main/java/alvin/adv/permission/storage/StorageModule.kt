package alvin.adv.permission.storage

import alvin.adv.permission.storage.persenters.StoragePresenter
import alvin.adv.permission.storage.views.StorageActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface StorageModule {

    @ContributesAndroidInjector(modules = [ViewModule::class, ProvidersModule::class])
    fun storageActivity(): StorageActivity

    @Module
    interface ViewModule {
        @Binds
        fun view(activity: StorageActivity): StorageContracts.View

        @Binds
        fun presenter(presenter: StoragePresenter): StorageContracts.Presenter
    }

    @Module
    class ProvidersModule
}
