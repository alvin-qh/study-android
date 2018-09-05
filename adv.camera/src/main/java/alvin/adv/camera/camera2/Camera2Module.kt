package alvin.adv.camera.camera2

import alvin.adv.camera.camera2.presenters.Camera2Presenter
import alvin.adv.camera.camera2.views.Camera2Activity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface Camera2Module {

    @ContributesAndroidInjector(modules = [
        ViewModule::class,
        ProvidesModule::class
    ])
    fun camera2Activity(): Camera2Activity

    @Module
    interface ViewModule {

        @Binds
        fun view(activity: Camera2Activity): Camera2Contracts.View

        @Binds
        fun presenter(presenter: Camera2Presenter): Camera2Contracts.Presenter
    }

    @Module
    class ProvidesModule
}
