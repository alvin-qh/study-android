package alvin.base.net.http;

import android.support.annotation.NonNull;

import alvin.base.net.http.common.domain.models.LiveWeather;
import alvin.lib.mvp.contracts.IPresenter;
import alvin.lib.mvp.contracts.IView;

public interface WeatherContracts {

    interface View extends IView {
        void showLiveWeather(@NonNull LiveWeather weather);

        void showError(@NonNull Throwable error);
    }

    interface Presenter extends IPresenter {
        void getLiveWeather();
    }
}
