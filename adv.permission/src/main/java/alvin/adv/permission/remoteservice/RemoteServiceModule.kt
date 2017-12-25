package alvin.adv.permission.remoteservice

import alvin.adv.permission.remoteservice.presenters.RemoteServicePresenter
import alvin.adv.permission.remoteservice.views.RemoteServiceActivityAdapter
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface RemoteServiceModule {

    @ContributesAndroidInjector(modules = [ViewModule::class])
    fun remoteServiceActivity(): RemoteServiceActivityAdapter

    @Module
    interface ViewModule {

        @Binds
        fun view(activity: RemoteServiceActivityAdapter): RemoteServiceContracts.IView

        @Binds
        fun presenter(presenter: RemoteServicePresenter): RemoteServiceContracts.Presenter
    }
}
