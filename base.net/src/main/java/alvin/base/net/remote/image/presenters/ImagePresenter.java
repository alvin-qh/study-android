package alvin.base.net.remote.image.presenters;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.inject.Inject;

import alvin.base.net.remote.image.ImageContracts;
import alvin.base.net.remote.image.services.ImageLoader;
import alvin.lib.common.rx.RxDecorator;
import alvin.lib.common.utils.ApplicationConfig;
import alvin.lib.mvp.contracts.adapters.PresenterAdapter;
import io.reactivex.Single;

import static alvin.base.net.remote.image.ImageContracts.Presenter;

public class ImagePresenter
        extends PresenterAdapter<ImageContracts.View>
        implements Presenter {

    private static final int RETRY_TIMES = 3;

    private final List<String> imageUrls = new ArrayList<>();
    private final RxDecorator rxDecorator;
    private final ImageLoader imageLoader;

    @Inject
    public ImagePresenter(@NonNull final ImageContracts.View view,
                          @NonNull final RxDecorator rxDecorator,
                          @NonNull final ImageLoader imageLoader) {
        super(view);
        this.rxDecorator = rxDecorator;
        this.imageLoader = imageLoader;
    }

    @Override
    public void loadImageUrls() {
        rxDecorator.<List<String>>de(
                Single.create(emitter -> {
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

                })
        )
                .retry(RETRY_TIMES)
                .subscribe(urls -> {
                    imageUrls.clear();
                    imageUrls.addAll(urls);
                    with(ImageContracts.View::imageSrcLoaded);
                });
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
        rxDecorator.<Drawable>de(
                Single.create(emitter -> {
                    try {
                        emitter.onSuccess(imageLoader.load(url));
                    } catch (IOException e) {
                        emitter.onError(e);
                    }
                })
        )
                .retry(RETRY_TIMES)
                .subscribe(
                        callback::accept,
                        throwable -> with(view -> view.imageLoadFailed(url))
                );
    }
}
