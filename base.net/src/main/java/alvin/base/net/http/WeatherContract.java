package alvin.base.net.http;

import android.support.annotation.NonNull;

import alvin.base.net.http.models.LiveWeather;
import alvin.lib.mvp.IPresenter;
import alvin.lib.mvp.IView;

public interface WeatherContract {

    interface View extends IView {
        void showLiveWeather(@NonNull LiveWeather weather);
    }

    interface Presenter extends IPresenter {
        void getLiveWeather();
    }
}
