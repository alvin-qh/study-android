@file:Suppress("unused")

package alvin.base.kotlin.dagger

import alvin.base.kotlin.dagger.presenters.DaggerPresenter
import alvin.base.kotlin.dagger.views.DaggerActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface DaggerModule {

    @ContributesAndroidInjector(modules = [ViewModule::class, ProvidersModule::class])
    fun daggerActivity(): DaggerActivity

    @Module
    interface ViewModule {
        @Binds
        fun view(view: DaggerActivity): DaggerContracts.View

        @Binds
        fun presenter(presenter: DaggerPresenter): DaggerContracts.Presenter
    }

    @Module
    class ProvidersModule
}
