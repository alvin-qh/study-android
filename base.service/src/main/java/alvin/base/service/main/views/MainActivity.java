package alvin.adv.service.main.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import alvin.adv.service.R;
import alvin.adv.service.bind.views.BindActivity;
import alvin.adv.service.foreground.views.ForegroundActivity;
import alvin.adv.service.intent.views.IntentActivity;
import alvin.adv.service.lifecycle.views.LifecycleActivity;
import alvin.adv.service.remote.views.RemoteActivity;
import alvin.adv.service.working.view.WorkingActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        ButterKnife.bind(this);
    }

    @OnClick({
            R.id.btn_basic_service,
            R.id.btn_working_service,
            R.id.btn_bind_service,
            R.id.btn_intent_service,
            R.id.btn_remote_service,
            R.id.btn_foreground_service
    })
    public void onButtonsClick(Button b) {
        Intent intent;

        switch (b.getId()) {
        case R.id.btn_basic_service:
            intent = new Intent(this, LifecycleActivity.class);
            break;
        case R.id.btn_working_service:
            intent = new Intent(this, WorkingActivity.class);
            break;
        case R.id.btn_bind_service:
            intent = new Intent(this, BindActivity.class);
            break;
        case R.id.btn_intent_service:
            intent = new Intent(this, IntentActivity.class);
            break;
        case R.id.btn_remote_service:
            intent = new Intent(this, RemoteActivity.class);
            break;
        case R.id.btn_foreground_service:
            intent = new Intent(this, ForegroundActivity.class);
            break;
        default:
            intent = null;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
