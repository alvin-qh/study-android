package alvin.base.kotlin.main

import alvin.base.kotlin.main.views.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface MainModule {

    @ContributesAndroidInjector
    fun mainActivity(): MainActivity
}
