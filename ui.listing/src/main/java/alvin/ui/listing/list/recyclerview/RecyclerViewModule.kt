package alvin.ui.listing.list.recyclerview

import alvin.ui.listing.list.recyclerview.presenters.RecyclerviewPresenter
import alvin.ui.listing.list.recyclerview.views.RecyclerViewActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface RecyclerViewModule {

    @ContributesAndroidInjector(modules = [ViewModule::class])
    fun recyclerViewActivity(): RecyclerViewActivity

    @Module
    interface ViewModule {
        @Binds
        fun view(activity: RecyclerViewActivity): RecyclerViewContracts.View

        @Binds
        fun presenter(presenter: RecyclerviewPresenter): RecyclerViewContracts.Presenter
    }
}
