package alvin.adv.permission.remoteservice

import alvin.adv.permission.remoteservice.presenters.RemoteServicePresenter
import alvin.adv.permission.remoteservice.views.RemoteServiceActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface RemoteServiceModule {

    @ContributesAndroidInjector(modules = [ViewModule::class])
    fun remoteServiceActivity(): RemoteServiceActivity

    @Module
    interface ViewModule {

        @Binds
        fun view(activity: RemoteServiceActivity): RemoteServiceContracts.IView

        @Binds
        fun presenter(presenter: RemoteServicePresenter): RemoteServiceContracts.Presenter
    }
}
