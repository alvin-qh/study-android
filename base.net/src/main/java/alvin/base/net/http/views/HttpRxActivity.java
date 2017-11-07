package alvin.base.net.http.views;

import alvin.base.net.http.WeatherContract;
import alvin.base.net.http.presenters.WeatherRxPresenter;

public class HttpRxActivity extends HttpBaseActivity {

    @Override
    protected WeatherContract.Presenter getPresenter() {
        return new WeatherRxPresenter(this);
    }
}
