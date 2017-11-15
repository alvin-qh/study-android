package alvin.base.net.http.task.views;

import alvin.base.net.http.WeatherContract;
import alvin.base.net.http.common.views.BaseActivity;
import alvin.base.net.http.task.presenters.TaskPresenter;

public class TaskActivity extends BaseActivity {

    @Override
    protected WeatherContract.Presenter getPresenter() {
        return new TaskPresenter(this);
    }
}
