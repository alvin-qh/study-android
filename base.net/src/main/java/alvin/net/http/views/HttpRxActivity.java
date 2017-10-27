package alvin.net.http.views;

import alvin.net.http.WeatherContract;
import alvin.net.http.presenters.WeatherTaskPresenter;

public class HttpRxActivity extends HttpBaseActivity {

    @Override
    protected WeatherContract.Presenter getPresenter() {
        return new WeatherTaskPresenter(this);
    }
}
