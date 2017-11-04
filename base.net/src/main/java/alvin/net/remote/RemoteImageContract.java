package alvin.net.remote;

public final class RemoteImageContract {

    public interface View {
        void imageSrcLoaded();
    }

    public interface Presenter {

        void doCreate();

        void doStart();

        void doStop();

        void doDestroy();

        String getImageSrcAt(int position);

        int getImageCount();
    }
}
