package alvin.base.net.remote.presenters;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.inject.Inject;

import alvin.base.net.remote.network.ImageLoader;
import alvin.lib.common.rx.RxDecorator;
import alvin.lib.common.rx.RxType;
import alvin.lib.common.utils.ApplicationConfig;
import alvin.lib.mvp.contracts.adapters.PresenterAdapter;
import io.reactivex.Single;

import static alvin.base.net.remote.RemoteContracts.Presenter;
import static alvin.base.net.remote.RemoteContracts.View;

public class RemotePresenter extends PresenterAdapter<View> implements Presenter {
    private final List<String> imageUrls = new ArrayList<>();
    private final RxDecorator.Builder rxDecoratorBuilder;
    private final ImageLoader imageLoader;

    @Inject
    public RemotePresenter(View view,
                           @RxType.IO RxDecorator.Builder rxDecoratorBuilder,
                           ImageLoader imageLoader) {
        super(view);
        this.rxDecoratorBuilder = rxDecoratorBuilder;
        this.imageLoader = imageLoader;
    }

    @Override
    @SuppressLint("CheckResult")
    public void loadImageUrls() {
        final RxDecorator decorator = rxDecoratorBuilder.build();

        decorator.<List<String>>de(
                Single.create(emitter -> {
                    ApplicationConfig config = ApplicationConfig.getInstance();
                    List<String> urls = new ArrayList<>();

                    String url;
                    int index = 1;
                    do {
                        url = config.get("remote.image." + index++);
                        if (url != null) {
                            urls.add(url);
                        }
                    } while (url != null);

                    emitter.onSuccess(urls);
                })
        ).subscribe(urls -> {
            imageUrls.clear();
            imageUrls.addAll(urls);
            with(View::imageSrcLoaded);
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
    @SuppressLint("CheckResult")
    public void loadImageAsDrawable(String url, Consumer<Drawable> callback) {
        final RxDecorator decorator = rxDecoratorBuilder.build();

        decorator.<Drawable>de(
                Single.create(emitter -> {
                    try {
                        emitter.onSuccess(imageLoader.load(url));
                    } catch (IOException e) {
                        emitter.onError(e);
                    }
                })
        ).subscribe(
                callback::accept,
                throwable -> with(view -> view.imageLoadFailed(url))
        );
    }
}
