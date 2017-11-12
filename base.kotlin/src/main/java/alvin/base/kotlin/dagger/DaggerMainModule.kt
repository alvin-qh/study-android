package alvin.base.kotlin.dagger

import alvin.base.kotlin.lib.common.rx.RxManager
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Module
class DaggerMainModule {

    @Provides
    fun rxManager(): RxManager {
        return RxManager.newBuilder()
                .withSubscribeOn { Schedulers.io() }
                .withObserveOn { AndroidSchedulers.mainThread() }
                .build()
    }
}