package alvin.base.service.basic.views;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import java.time.ZoneOffset;

import javax.inject.Inject;

import alvin.base.service.R;
import alvin.base.service.basic.BasicContracts;
import alvin.base.service.basic.services.BasicService;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class BasicActivity extends AppCompatActivity implements BasicContracts.View {

    @Inject BasicContracts.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.basic_activity);
        ButterKnife.bind(this);

        AndroidInjection.inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        presenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

        presenter.onStop();
    }

    @Override
    public Context context() {
        return this;
    }

    @OnClick(R.id.btn_start_service)
    public void onStartButtonClick(Button b) {
        final Intent intent = new Intent(this, BasicService.class);
        intent.putExtra(BasicService.EXTRA_ARGUMENTS_MODE, Service.START_REDELIVER_INTENT);
        intent.putExtra(BasicService.EXTRA_ARGUMENTS_TIMEZONE, ZoneOffset.systemDefault().getId());
        startService(intent);
    }

    @OnClick(R.id.btn_stop_service)
    public void onStopButtonClick(Button b) {
        stopService(new Intent(this, BasicService.class));
    }

    @Override
    public void serviceStarted() {

    }

    @Override
    public void serviceDestroyed() {

    }
}
