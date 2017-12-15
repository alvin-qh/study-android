package alvin.ui.listing.recycler

import alvin.ui.listing.recycler.views.RecyclerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface RecyclerModule {

    @ContributesAndroidInjector
    fun recyclerActivity(): RecyclerActivity
}
