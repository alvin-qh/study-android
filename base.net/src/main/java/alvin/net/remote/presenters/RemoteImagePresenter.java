package alvin.net.remote.presenters;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import alvin.common.rx.RxManager;
import alvin.common.rx.SingleSubscriber;
import alvin.common.util.ApplicationConfig;
import alvin.common.util.Cache;
import alvin.net.remote.RemoteImageContract;
import alvin.net.remote.images.RemoteImageLoader;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RemoteImagePresenter implements RemoteImageContract.Presenter {
    private static final String CACHE_DIR_NAME = "images";

    private final WeakReference<RemoteImageContract.View> viewRef;
    private final List<String> imageUrls = new ArrayList<>();

    private final RxManager rxManager = RxManager.newBuilder()
            .withSubscribeOn(Schedulers::io)
            .withObserveOn(AndroidSchedulers::mainThread)
            .build();

    private RemoteImageLoader imageLoader;

    public RemoteImagePresenter(RemoteImageContract.View view) {
        this.viewRef = new WeakReference<>(view);
    }

    private void withView(Consumer<RemoteImageContract.View> consumer) {
        RemoteImageContract.View view = viewRef.get();
        if (view != null) {
            consumer.accept(view);
        }
    }

    @Override
    public void doCreate() {
        withView(view -> {
            File cacheFileDir = createCacheFileDir(view.context());
            this.imageLoader = new RemoteImageLoader(
                    cacheFileDir.getAbsolutePath(),
                    new Cache<>(1L, ChronoUnit.HOURS));
        });
    }

    private File createCacheFileDir(Context context) {
        File imageCacheDir = new File(context.getExternalCacheDir(), CACHE_DIR_NAME);
        if (!imageCacheDir.exists()) {
            imageCacheDir.mkdirs();
        }
        return imageCacheDir;
    }

    @Override
    public void doStart() {
        loadImageUrls();
    }

    private void loadImageUrls() {
        final SingleSubscriber<List<String>> subscriber = rxManager.single(
                single -> single.retry(3),
                emitter -> {
                    ApplicationConfig config = ApplicationConfig.getInstance();
                    List<String> urls = new ArrayList<>();

                    int index = 1;
                    String url;
                    do {
                        url = config.get("remote.image." + index++);
                        if (url != null) {
                            urls.add(url);
                        }
                    } while (url != null);

                    emitter.onSuccess(urls);
                }
        );

        subscriber.subscribe(
                urls -> {
                    imageUrls.clear();
                    imageUrls.addAll(urls);
                    withView(RemoteImageContract.View::imageSrcLoaded);
                }
        );
    }

    @Override
    public void doStop() {
    }

    @Override
    public void doDestroy() {
        rxManager.clear();

        if (imageLoader != null) {
            imageLoader.dispose();
        }
    }

    @Override
    public String getImageSrcAt(int position) {
        return imageUrls.get(position);
    }

    @Override
    public int getImageCount() {
        return imageUrls.size();
    }

    @Override
    public void loadImageAsDrawable(String url, Consumer<Drawable> callback) {
        final SingleSubscriber<Drawable> subscriber = rxManager.single(
                single -> single.retry(3),
                emitter -> {
                    try {
                        emitter.onSuccess(imageLoader.load(url));
                    } catch (IOException e) {
                        emitter.onError(e);
                    }
                }
        );

        subscriber.subscribe(
                callback::accept,
                throwable -> withView(view -> view.imageLoadFailed(url))
        );
    }
}
