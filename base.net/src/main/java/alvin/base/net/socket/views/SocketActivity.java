package alvin.adv.net.socket.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import alvin.adv.net.R;
import alvin.adv.net.socket.jnative.views.NativeActivity;
import alvin.adv.net.socket.netty.views.NettyActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;

public class SocketActivity extends DaggerAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.socket_activity);

        ButterKnife.bind(this);
    }

    @OnClick({
            R.id.btn_native_socket,
            R.id.btn_netty,
    })
    public void onButtonsClick(Button b) {
        Intent intent;
        switch (b.getId()) {
        case R.id.btn_native_socket:
            intent = new Intent(this, NativeActivity.class);
            break;
        case R.id.btn_netty:
            intent = new Intent(this, NettyActivity.class);
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
