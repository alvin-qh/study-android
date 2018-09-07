package alvin.base.net.socket.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import alvin.base.net.R;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SocketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);

        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_native_socket, R.id.btn_netty})
    public void onButtonsClick(Button b) {
        Intent intent;
        switch (b.getId()) {
        case R.id.btn_native_socket:
            intent = new Intent(this, SocketNativeActivity.class);
            break;
        case R.id.btn_netty:
            intent = new Intent(this, SocketNettyActivity.class);
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
