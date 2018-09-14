package alvin.base.net.remote;

import android.content.Context;

import java.io.File;
import java.time.temporal.ChronoUnit;

import alvin.base.net.remote.network.ImageLoader;
import alvin.base.net.remote.presenters.RemotePresenter;
import alvin.base.net.remote.views.RemoteActivity;
import alvin.lib.common.rx.RxDecorator;
import alvin.lib.common.rx.RxType;
import alvin.lib.common.utils.Cache;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public interface RemoteModule {

    @ContributesAndroidInjector(modules = ActivityModule.class)
    RemoteActivity activity();

    @Module
    interface ActivityModule {
        int RETRY_TIMES = 3;

        @Binds
        RemoteContracts.View view(final RemoteActivity activity);

        @Binds
        RemoteContracts.Presenter presenter(final RemotePresenter presenter);

        @Provides
        @RxType.IO
        static RxDecorator.Builder rxDecoratorBuilder() {
            return RxDecorator.newBuilder()
                    .subscribeOn(Schedulers::io)
                    .observeOn(AndroidSchedulers::mainThread)
                    .retryTimes(RETRY_TIMES);
        }

        @Provides
        static ImageLoader imageLoader(final Context context) {
            final File cachePath = new File(context.getExternalCacheDir(), "images");
            if (!cachePath.exists() && !cachePath.mkdirs()) {
                throw new RuntimeException("Cannot create cache directory");
            }
            return new ImageLoader(cachePath.getAbsolutePath(), new Cache<>(1L, ChronoUnit.HOURS));
        }
    }
}
