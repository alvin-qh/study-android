package alvin.adv.camera.main

import alvin.adv.camera.main.presenters.MainPresenter
import alvin.adv.camera.main.views.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface MainModule {

    @ContributesAndroidInjector(modules = [ViewModule::class])
    fun mainActivity(): MainActivity

    @Module
    interface ViewModule {

        @Binds
        fun view(activity: MainActivity): MainContracts.View

        @Binds
        fun presenter(presenter: MainPresenter): MainContracts.Presenter
    }
}
