package alvin.base.net.remote.image.presenters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import alvin.base.net.remote.image.services.ImageLoader;
import alvin.lib.common.rx.RxManager;
import alvin.lib.common.rx.SingleSubscriber;
import alvin.lib.common.util.ApplicationConfig;
import alvin.lib.common.util.Cache;
import alvin.lib.mvp.PresenterAdapter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static alvin.base.net.remote.image.ImageContract.Presenter;
import static alvin.base.net.remote.image.ImageContract.View;

public class ImagePresenter extends PresenterAdapter<View> implements Presenter {

    private static final String CACHE_DIR_NAME = "images";
    private static final int RETRY_TIMES = 3;

    private final List<String> imageUrls = new ArrayList<>();

    private final RxManager rxManager = RxManager.newBuilder()
            .withSubscribeOn(Schedulers::io)
            .withObserveOn(AndroidSchedulers::mainThread)
            .build();

    private ImageLoader imageLoader;

    public ImagePresenter(@NonNull View view) {
        super(view);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        withView(view -> {
            File cacheFileDir = createCacheFileDir(view.context());
            this.imageLoader = new ImageLoader(
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
    public void onStart() {
        super.onStart();
        loadImageUrls();
    }

    private void loadImageUrls() {
        final SingleSubscriber<List<String>> subscriber = rxManager.single(
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

        subscriber.config(single -> single.retry(RETRY_TIMES))
                .subscribe(
                        urls -> {
                            imageUrls.clear();
                            imageUrls.addAll(urls);
                            withView(View::imageSrcLoaded);
                        }
                );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

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
                emitter -> {
                    try {
                        emitter.onSuccess(imageLoader.load(url));
                    } catch (IOException e) {
                        emitter.onError(e);
                    }
                }
        );

        subscriber
                .config(single -> single.retry(RETRY_TIMES))
                .subscribe(
                        callback::accept,
                        throwable -> withView(view -> view.imageLoadFailed(url))
                );
    }
}
