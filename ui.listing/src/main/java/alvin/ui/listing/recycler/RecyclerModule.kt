package alvin.ui.listing.recycler

import alvin.ui.listing.recycler.views.RecyclerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class RecyclerModule {

    @ContributesAndroidInjector
    abstract fun recyclerActivity(): RecyclerActivity
}
