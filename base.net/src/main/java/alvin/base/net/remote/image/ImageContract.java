package alvin.base.net.remote.image;

import android.graphics.drawable.Drawable;

import java.util.function.Consumer;

import alvin.lib.mvp.contracts.IPresenter;
import alvin.lib.mvp.contracts.IView;

public interface ImageContract {

    interface View extends IView {

        void imageSrcLoaded();

        void imageLoadFailed(String imageSrc);
    }

    interface Presenter extends IPresenter {

        String getImageSrcAt(int position);

        int getImageCount();

        void loadImageAsDrawable(String url, Consumer<Drawable> callback);
    }
}
