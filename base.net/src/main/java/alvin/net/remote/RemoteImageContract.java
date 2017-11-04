package alvin.net.remote;

import java.util.Collection;

public final class RemoteImageContract {

    public interface View {
    }

    public interface Presenter {
        Collection<String> getImageUrls();

        String getImageUrlAt(int position);
    }
}
