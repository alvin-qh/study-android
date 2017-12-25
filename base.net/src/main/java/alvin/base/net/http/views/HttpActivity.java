package alvin.base.net.http.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import alvin.base.net.R;
import alvin.base.net.http.rx.views.RxActivity;
import alvin.base.net.http.task.views.TaskActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;

public class HttpActivity extends DaggerAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.http_activity);

        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_use_sync_task, R.id.btn_use_rx})
    public void onButtonsClick(Button b) {
        Intent intent;

        switch (b.getId()) {
        case R.id.btn_use_sync_task:
            intent = new Intent(this, TaskActivity.class);
            break;
        case R.id.btn_use_rx:
            intent = new Intent(this, RxActivity.class);
            break;
        default:
            intent = null;
            break;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
