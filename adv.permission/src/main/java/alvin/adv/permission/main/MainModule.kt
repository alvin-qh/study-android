package alvin.adv.permission.main

import alvin.adv.permission.main.views.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface MainModule {

    @ContributesAndroidInjector
    fun mainActivity(): MainActivity
}
