package alvin.base.net.http.rx.views;

import alvin.base.net.http.WeatherContract;
import alvin.base.net.http.common.views.BaseActivity;
import alvin.base.net.http.rx.presenters.RxPresenter;

public class RxActivity extends BaseActivity {

    @Override
    protected WeatherContract.Presenter getPresenter() {
        return new RxPresenter(this);
    }
}
