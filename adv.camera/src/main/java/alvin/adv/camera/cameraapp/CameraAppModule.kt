package alvin.adv.camera.cameraapp

import alvin.adv.camera.cameraapp.presenters.CameraAppPresenter
import alvin.adv.camera.cameraapp.views.CameraAppActivity
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import javax.inject.Named

@Module
interface CameraAppModule {

    @ContributesAndroidInjector(modules = [
        ViewModule::class,
        ConfigModule::class
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
    class ConfigModule {
        @Provides
        @Named("photo_save_file_pattern")
        fun photoSaveFilePattern(): String {
            return "yyyyMMddHHmmssSSS"
        }
    }
}
