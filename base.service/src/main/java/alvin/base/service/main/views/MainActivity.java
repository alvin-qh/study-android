package alvin.base.service.main.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import alvin.base.service.R;
import alvin.base.service.bind.views.BindActivity;
import alvin.base.service.intent.views.IntentActivity;
import alvin.base.service.lifecycle.views.LifecycleActivity;
import alvin.base.service.working.view.WorkingActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

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
            R.id.btn_intent_service
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
        default:
            intent = null;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
