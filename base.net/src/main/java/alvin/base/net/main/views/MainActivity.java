package alvin.adv.net.main.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import alvin.adv.net.R;
import alvin.adv.net.http.views.HttpActivity;
import alvin.adv.net.remote.views.RemoteActivity;
import alvin.adv.net.socket.views.SocketActivity;
import alvin.adv.net.status.views.StatusActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        ButterKnife.bind(this);
    }

    @OnClick({
            R.id.btn_network_status,
            R.id.btn_http,
            R.id.btn_socket,
            R.id.btn_remote_data
    })
    public void onButtonsClicked(Button b) {
        Intent intent;

        switch (b.getId()) {
        case R.id.btn_network_status:
            intent = new Intent(this, StatusActivity.class);
            break;
        case R.id.btn_http:
            intent = new Intent(this, HttpActivity.class);
            break;
        case R.id.btn_socket:
            intent = new Intent(this, SocketActivity.class);
            break;
        case R.id.btn_remote_data:
            intent = new Intent(this, RemoteActivity.class);
            break;
        default:
            intent = null;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
