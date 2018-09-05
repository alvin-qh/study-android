package alvin.adv.camera.cameraapp

import alvin.adv.camera.cameraapp.presenters.CameraAppPresenter
import alvin.adv.camera.cameraapp.views.CameraAppActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface CameraAppModule {

    @ContributesAndroidInjector(modules = [
        ViewModule::class,
        ProvidesModule::class
    ])
    fun cameraAppActivity(): CameraAppActivity

    @Module
    interface ViewModule {
        @Binds
        fun view(activity: CameraAppActivity): CameraAppContracts.View

        @Binds
        fun presenter(presenter: CameraAppPresenter): CameraAppContracts.Presenter
    }

    @Module
    class ProvidesModule
}
