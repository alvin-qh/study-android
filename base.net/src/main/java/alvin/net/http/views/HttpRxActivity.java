package alvin.net.http.views;

import alvin.net.http.WeatherContract;
import alvin.net.http.presenters.WeatherRxPresenter;

public class HttpRxActivity extends HttpBaseActivity {

    @Override
    protected WeatherContract.Presenter getPresenter() {
        return new WeatherRxPresenter(this);
    }
}
