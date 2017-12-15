package alvin.ui.listing.list

import alvin.ui.listing.list.views.ListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ListModule {

    @ContributesAndroidInjector
    fun listActivity(): ListActivity
}
