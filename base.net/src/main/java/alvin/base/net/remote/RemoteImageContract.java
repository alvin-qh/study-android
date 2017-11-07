package alvin.base.net.remote;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.function.Consumer;

public final class RemoteImageContract {

    private RemoteImageContract() {
    }

    public interface View {
        Context context();

        void imageSrcLoaded();

        void imageLoadFailed(String imageSrc);
    }

    public interface Presenter {

        void doCreate();

        void doStart();

        void doStop();

        void doDestroy();

        String getImageSrcAt(int position);

        int getImageCount();

        void loadImageAsDrawable(String url, Consumer<Drawable> callback);
    }
}
