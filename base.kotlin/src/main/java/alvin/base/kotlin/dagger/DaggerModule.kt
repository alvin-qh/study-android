package alvin.base.kotlin.dagger

import alvin.base.kotlin.dagger.presenters.DaggerPresenter
import alvin.base.kotlin.dagger.views.DaggerActivity
import alvin.base.kotlin.lib.common.rx.RxManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Module(includes = arrayOf(DaggerModule.BindModule::class))
class DaggerModule {

    @Provides
    fun rxManager(): RxManager {
        return RxManager.newBuilder()
                .withSubscribeOn { Schedulers.io() }
                .withObserveOn { AndroidSchedulers.mainThread() }
                .build()
    }

    @Module
    interface BindModule {

        @Binds
        fun view(view: DaggerActivity): DaggerContracts.View

        @Binds
        fun presenter(presenter: DaggerPresenter): DaggerContracts.Presenter
    }
}
