package alvin.base.service;

import javax.inject.Singleton;

import alvin.lib.common.rx.RxDecorator;
import alvin.lib.common.rx.RxType;
import dagger.Module;
import dagger.Provides;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public class ApplicationModule {
    @Provides
    @Singleton
    @RxType.IO
    public RxDecorator.Builder rxDecoratorBuilder() {
        return RxDecorator.newBuilder()
                .subscribeOn(Schedulers::io)
                .observeOn(AndroidSchedulers::mainThread);
    }
}
