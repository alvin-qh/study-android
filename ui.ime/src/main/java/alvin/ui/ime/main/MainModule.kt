package alvin.ui.ime.main

import alvin.ui.ime.main.presenters.MainPresenter
import alvin.ui.ime.main.views.MainActivityAdapter
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface MainModule {

    @ContributesAndroidInjector(modules = [ViewModule::class])
    fun mainActivity(): MainActivityAdapter

    @Module
    interface ViewModule {
        @Binds
        fun view(activity: MainActivityAdapter): MainContracts.IView

        @Binds
        fun presenter(presenter: MainPresenter): MainContracts.Presenter
    }
}
