package alvin.adv.service;

import javax.inject.Singleton;

import alvin.lib.common.rx.RxDecorator;
import alvin.lib.common.rx.RxType;
import alvin.lib.common.utils.Versions;
import dagger.Module;
import dagger.Provides;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public class ApplicationModule {

    @Provides
    @Singleton
    Versions version() {
        return Versions.VERSIONS_O;
    }

    @Provides
    @Singleton
    @RxType.IO
    public RxDecorator.Builder rxDecoratorBuilder() {
        return RxDecorator.newBuilder()
                .subscribeOn(Schedulers::io)
                .observeOn(AndroidSchedulers::mainThread);
    }
}
