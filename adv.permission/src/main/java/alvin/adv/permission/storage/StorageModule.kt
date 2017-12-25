package alvin.adv.permission.storage

import alvin.adv.permission.storage.persenters.StoragePresenter
import alvin.adv.permission.storage.views.StorageActivity
import alvin.lib.common.rx.RxDecorator
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Module
interface StorageModule {

    @ContributesAndroidInjector(modules = [ViewModule::class, ProvidersModule::class])
    fun storageActivity(): StorageActivity

    @Module
    interface ViewModule {
        @Binds
        fun view(activity: StorageActivity): StorageContracts.View

        @Binds
        fun presenter(presenter: StoragePresenter): StorageContracts.Presenter
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
