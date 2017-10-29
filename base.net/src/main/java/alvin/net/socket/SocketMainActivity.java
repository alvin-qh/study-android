package alvin.net.socket;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import alvin.net.R;
import alvin.net.socket.views.NativeSocketActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SocketMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_main);

        ButterKnife.bind(this);
    }

    @OnClick({
            R.id.btn_native_socket,
            R.id.btn_netty,
    })
    public void onButtonsClick(Button b) {
        Intent intent = null;
        switch (b.getId()) {
        case R.id.btn_native_socket:
            intent = new Intent(this, NativeSocketActivity.class);
            break;
        case R.id.btn_netty:
            break;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
