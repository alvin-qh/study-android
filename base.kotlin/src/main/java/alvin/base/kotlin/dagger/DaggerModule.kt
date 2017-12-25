package alvin.base.kotlin.dagger

import alvin.base.kotlin.dagger.presenters.DaggerPresenter
import alvin.base.kotlin.dagger.views.DaggerActivity
import alvin.lib.common.rx.RxDecorator
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Module
interface DaggerModule {

    @ContributesAndroidInjector(modules = [
        ViewModule::class,
        ProvidersModule::class
    ])
    fun daggerActivity(): DaggerActivity

    @Module
    interface ViewModule {
        @Binds
        fun view(view: DaggerActivity): DaggerContracts.View

        @Binds
        fun presenter(presenter: DaggerPresenter): DaggerContracts.Presenter
    }

    @Module
    class ProvidersModule {

        @Provides
        fun rxDecorator(): RxDecorator {
            return RxDecorator.newBuilder()
                    .subscribeOn { Schedulers.io() }
                    .observeOn { AndroidSchedulers.mainThread() }
                    .build()
        }
    }
}
