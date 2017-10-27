package alvin.net.http;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import alvin.net.R;
import alvin.net.http.presenters.WeatherPresenter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HttpMainActivity extends AppCompatActivity implements WeatherContract.View {

    private WeatherContract.Presenter presenter;

    @BindView(R.id.text_weather)
    TextView textWheather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_main);

        ButterKnife.bind(this);

        presenter = new WeatherPresenter(this);
        presenter.doCreate();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.showLiveWeather();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.doDestroy();
    }

    @OnClick(R.id.btn_refresh)
    public void onRefreshButtonClick(Button button) {
        presenter.showLiveWeather();
    }
}
