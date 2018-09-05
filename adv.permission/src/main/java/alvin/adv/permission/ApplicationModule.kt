package alvin.adv.permission

import alvin.lib.common.json.Jackson
import alvin.lib.common.rx.RxDecorator
import alvin.lib.common.rx.RxType
import alvin.lib.common.utils.Storages
import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
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
    fun storages(context: Context): Storages {
        return Storages(context)
    }

    @Singleton
    @Provides
    fun objectMapper(): ObjectMapper {
        return Jackson.create()
    }

    @Singleton
    @Provides
    @RxType.IO
    fun rxDecorator(): RxDecorator.Builder {
        return RxDecorator.newBuilder()
                .subscribeOn { Schedulers.io() }
                .observeOn { AndroidSchedulers.mainThread() }
    }
}
