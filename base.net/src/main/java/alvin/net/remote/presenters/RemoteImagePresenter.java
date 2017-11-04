package alvin.net.remote.presenters;

import java.util.Collection;

import alvin.net.remote.RemoteImageContract;

import static com.google.common.collect.Lists.newArrayList;

public class RemoteImagePresenter implements RemoteImageContract.Presenter {

    private static final String[] URLS = {"A", "B"};

    @Override
    public Collection<String> getImageUrls() {
        return newArrayList(URLS);
    }

    @Override
    public String getImageUrlAt(int position) {
        return URLS[position];
    }
}
