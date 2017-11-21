package alvin.base.service.lifecycle.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import javax.inject.Inject;

import alvin.base.service.R;
import alvin.base.service.lifecycle.LifecycleContracts;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;

public class LifecycleActivity extends DaggerAppCompatActivity implements LifecycleContracts.View {

    @Inject LifecycleContracts.Presenter presenter;

    @BindView(R.id.rg_service_status) RadioGroup rgServiceStatus;

    @BindView(R.id.tv_service_start_count) TextView tvStartCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.lifecycle_activity);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        presenter.registerReceiver(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        presenter.unregisterReceiver(this);
    }

    @Override
    public Context context() {
        return this;
    }

    @OnClick(R.id.btn_start_service)
    public void onStartButtonClick(Button b) {
        presenter.startService(this);
    }

    @OnClick(R.id.btn_bind_service)
    public void onBindButtonClick(Button b) {
        presenter.bindService(this);
    }

    @OnClick(R.id.btn_unbind_service)
    public void onUnbindButtonClick(Button b) {
        presenter.unbindService(this);
    }

    @OnClick(R.id.btn_stop_service)
    public void onStopButtonClick(Button b) {
        presenter.stopService(this);
    }

    @Override
    public void serviceDestroyed() {
        rgServiceStatus.check(R.id.rb_destroyed);
    }

    @Override
    public void serviceCreated() {
        rgServiceStatus.check(R.id.rb_created);
    }

    @Override
    public void showStartCount(int count) {
        tvStartCount.setText(String.valueOf(count));
    }
}
