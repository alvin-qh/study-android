package alvin.adv.camera

import alvin.adv.camera.camera2.Camera2Module
import alvin.adv.camera.cameraapp.CameraAppModule
import alvin.adv.camera.common.CommonModule
import alvin.adv.camera.main.MainModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ApplicationModule::class,
    CommonModule::class,
    MainModule::class,
    CameraAppModule::class,
    Camera2Module::class
])
interface ApplicationComponent : AndroidInjector<Application> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<Application>()
}
