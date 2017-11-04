package alvin.net.remote.presenters;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import alvin.common.util.ApplicationConfig;
import alvin.net.remote.RemoteImageContract;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RemoteImagePresenter implements RemoteImageContract.Presenter {

    private final WeakReference<RemoteImageContract.View> viewRef;
    private final List<String> imageUrls = new ArrayList<>();

    private Disposable dispLoadImages;

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
    }

    @Override
    public void doStart() {
        loadImagesAsDrawables();
    }

    @Override
    public void doStop() {
    }

    @Override
    public void doDestroy() {
        if (dispLoadImages != null) {
            dispLoadImages.dispose();
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

    private void loadImagesAsDrawables() {
        dispLoadImages = Single.<List<String>>create(
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
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(urls -> {
                    imageUrls.clear();
                    imageUrls.addAll(urls);
                    withView(RemoteImageContract.View::imageSrcLoaded);
                });
    }
}
