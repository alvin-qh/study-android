package alvin.base.net.http.views;

import alvin.base.net.http.WeatherContract;
import alvin.base.net.http.presenters.WeatherTaskPresenter;

public class HttpTaskActivity extends HttpBaseActivity {

    @Override
    protected WeatherContract.Presenter getPresenter() {
        return new WeatherTaskPresenter(this);
    }
}
