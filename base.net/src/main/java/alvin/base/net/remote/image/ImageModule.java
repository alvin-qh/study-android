package alvin.adv.net.remote.image;

import android.content.Context;

import java.io.File;
import java.time.temporal.ChronoUnit;

import alvin.adv.net.remote.image.presenters.ImagePresenter;
import alvin.adv.net.remote.image.services.ImageLoader;
import alvin.adv.net.remote.image.views.ImageActivity;
import alvin.lib.common.rx.RxDecorator;
import alvin.lib.common.utils.Cache;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public interface ImageModule {

    @ContributesAndroidInjector(modules = {
            BindModule.class,
            ProvidesModule.class
    })
    ImageActivity activity();

    @Module
    interface BindModule {
        @Binds
        ImageContracts.View view(final ImageActivity activity);

        @Binds
        ImageContracts.Presenter presenter(final ImagePresenter presenter);
    }

    @Module
    class ProvidesModule {

        @Provides
        public RxDecorator rxDecorator() {
            return RxDecorator.newBuilder()
                    .subscribeOn(Schedulers::io)
                    .observeOn(AndroidSchedulers::mainThread)
                    .build();
        }

        @Provides
        public ImageLoader imageLoader(final Context context) {
            final File cachePath = new File(context.getExternalCacheDir(), "images");
            if (!cachePath.exists() && !cachePath.mkdirs()) {
                throw new RuntimeException("Cannot create cache directory");
            }
            return new ImageLoader(cachePath.getAbsolutePath(),
                    new Cache<>(1L, ChronoUnit.HOURS));
        }
    }
}
