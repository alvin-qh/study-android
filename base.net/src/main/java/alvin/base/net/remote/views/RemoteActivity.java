package alvin.base.net.remote.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import alvin.base.net.R;
import alvin.base.net.remote.image.views.ImageActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;

public class RemoteActivity extends DaggerAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.remote_activity);

        ButterKnife.bind(this);
    }

    @OnClick({
            R.id.btn_remote_image
    })
    public void onButtonsClick(Button b) {
        Intent intent;

        switch (b.getId()) {
        case R.id.btn_remote_image:
            intent = new Intent(this, ImageActivity.class);
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
