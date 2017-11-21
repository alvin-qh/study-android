package alvin.base.service.foreground.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import javax.inject.Inject;

import alvin.base.service.R;
import alvin.base.service.foreground.ForegroundContracts;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;

public class ForegroundActivity extends DaggerAppCompatActivity
        implements ForegroundContracts.View {

    @Inject ForegroundContracts.Presenter presenter;

    @BindView(R.id.btn_start_service) Button btnStartService;
    @BindView(R.id.btn_stop_service) Button btnStopService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.foreground_activity);

        ButterKnife.bind(this);

        presenter.startReceiver(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        presenter.stopReceiver(this);
    }

    @Override
    public Context context() {
        return this;
    }

    @OnClick(R.id.btn_start_service)
    public void onStartServiceButtonClick(Button b) {
        presenter.startService(this);
    }

    @OnClick(R.id.btn_stop_service)
    public void onStopServiceButtonClick(Button b) {
        presenter.stopService(this);
    }

    @Override
    public void serviceCreated() {
        btnStartService.setEnabled(false);
        btnStopService.setEnabled(true);
    }

    @Override
    public void serviceDestroyed() {
        btnStartService.setEnabled(true);
        btnStopService.setEnabled(false);
    }
}
