package alvin.ui.listing.list.listview

import alvin.ui.listing.list.listview.persenters.ListViewPresenter
import alvin.ui.listing.list.listview.views.ListViewActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ListViewModule {

    @ContributesAndroidInjector(modules = [ViewModule::class])
    fun listViewActivity(): ListViewActivity

    @Module
    interface ViewModule {

        @Binds
        fun view(activity: ListViewActivity): ListViewContracts.View

        @Binds
        fun presenter(presenter: ListViewPresenter): ListViewContracts.Presenter
    }
}
