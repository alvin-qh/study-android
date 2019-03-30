package alvin.base.net.main.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import alvin.base.net.R;
import alvin.base.net.http.views.HttpActivity;
import alvin.base.net.remote.views.RemoteActivity;
import alvin.base.net.socket.views.SocketActivity;
import alvin.base.net.status.views.NetworkStatusActivity;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_main);

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
            intent = new Intent(this, NetworkStatusActivity.class);
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
