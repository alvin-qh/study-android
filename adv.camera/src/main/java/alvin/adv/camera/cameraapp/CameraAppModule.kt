package alvin.adv.camera.cameraapp

import alvin.adv.camera.cameraapp.presenters.CameraAppPresenter
import alvin.adv.camera.cameraapp.views.CameraAppActivity
import alvin.base.kotlin.lib.common.rx.RxManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named

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
    class ProvidesModule {

        @Provides
        @Named("photo_save_file_pattern")
        fun photoSaveFilePattern(): String {
            return "yyyyMMddHHmmssSSS"
        }

        @Provides
        fun rxManager(): RxManager {
            return RxManager.newBuilder()
                    .subscribeOn { Schedulers.io() }
                    .observeOn { AndroidSchedulers.mainThread() }
                    .build()
        }
    }
}
