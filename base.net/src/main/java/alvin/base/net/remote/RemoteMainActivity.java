package alvin.base.net.remote;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import alvin.base.net.R;
import alvin.base.net.remote.views.RemoteImageActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RemoteMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_remote_main);

        ButterKnife.bind(this);
    }

    @OnClick({
            R.id.btn_remote_image
    })
    public void onButtonsClick(Button b) {
        Intent intent = null;

        switch (b.getId()) {
        case R.id.btn_remote_image:
            intent = new Intent(this, RemoteImageActivity.class);
            break;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
