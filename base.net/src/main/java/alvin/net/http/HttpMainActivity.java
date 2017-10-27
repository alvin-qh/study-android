package alvin.net.http;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import alvin.net.R;
import alvin.net.http.views.HttpRxActivity;
import alvin.net.http.views.HttpTaskActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HttpMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_main);

        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_use_sync_task, R.id.btn_use_rx})
    public void onButtonsClick(Button b) {
        Intent intent = null;

        switch (b.getId()) {
        case R.id.btn_use_sync_task:
            intent = new Intent(this, HttpTaskActivity.class);
            break;
        case R.id.btn_use_rx:
            intent = new Intent(this, HttpRxActivity.class);
            break;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
