@file:Suppress("unused")

package alvin.base.kotlin.dbflow

import alvin.base.kotlin.dbflow.presenters.DBFlowPresenter
import alvin.base.kotlin.dbflow.views.DBFlowActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface DBFlowModule {

    @ContributesAndroidInjector(modules = [ViewModule::class])
    fun dBFlowActivity(): DBFlowActivity

    @Module
    abstract class ViewModule {

        @Binds
        abstract fun view(activity: DBFlowActivity): DBFlowContract.View

        @Binds
        abstract fun presenter(presenter: DBFlowPresenter): DBFlowContract.Presenter
    }
}
