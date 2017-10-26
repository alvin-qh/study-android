package alvin.net;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import alvin.net.status.StatusMainActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @OnClick({
            R.id.btn_network_status,
            R.id.btn_http,
            R.id.btn_socket
    })
    public void onButtonsClicked(Button b) {
        Intent intent = null;
        switch (b.getId()) {
        case R.id.btn_network_status:
            intent = new Intent(this, StatusMainActivity.class);
            break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
