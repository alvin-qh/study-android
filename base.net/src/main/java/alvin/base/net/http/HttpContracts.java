package alvin.base.net.http;

import android.support.annotation.NonNull;

import alvin.base.net.http.domain.models.LiveWeather;
import alvin.lib.mvp.contracts.IPresenter;
import alvin.lib.mvp.contracts.IView;

public interface HttpContracts {

    interface View extends IView {
        void showLiveWeather(@NonNull LiveWeather weather);

        void showError(@NonNull Throwable error);
    }

    interface Presenter extends IPresenter {
        void getLiveWeather();
    }
}
