package alvin.adv.camera

import alvin.lib.common.rx.RxDecorator
import alvin.lib.common.rx.RxType
import alvin.lib.common.utils.PackageManagers
import alvin.lib.common.utils.Storages
import alvin.lib.common.utils.SystemServices
import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Singleton
    @Provides
    fun context(application: Application): Context {
        return application
    }

    @Singleton
    @Provides
    fun systemServices(context: Context): SystemServices {
        return SystemServices(context)
    }

    @Singleton
    @Provides
    fun packageManagers(context: Context): PackageManagers {
        return PackageManagers(context)
    }

    @Singleton
    @Provides
    fun storages(context: Context): Storages {
        return Storages(context)
    }

    @Provides
    @Singleton
    @RxType.IO
    fun rxDecoratorBuilder(): RxDecorator.Builder {
        return RxDecorator.newBuilder()
                .subscribeOn { Schedulers.io() }
                .observeOn { AndroidSchedulers.mainThread() }
    }
}
