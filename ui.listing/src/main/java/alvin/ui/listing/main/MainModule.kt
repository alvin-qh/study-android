package alvin.ui.listing.main

import alvin.ui.listing.main.views.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface MainModule {

    @ContributesAndroidInjector
    fun mainActivity(): MainActivity
}
