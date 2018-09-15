@file:Suppress("unused")

package alvin.base.kotlin.dagger

import alvin.base.kotlin.dagger.presenters.DaggerPresenter
import alvin.base.kotlin.dagger.views.DaggerActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface DaggerModule {

    @ContributesAndroidInjector(modules = [ActivityModule::class])
    fun daggerActivity(): DaggerActivity

    @Module
    abstract class ActivityModule {
        @Binds
        abstract fun view(view: DaggerActivity): DaggerContracts.View

        @Binds
        abstract fun presenter(presenter: DaggerPresenter): DaggerContracts.Presenter
    }
}
