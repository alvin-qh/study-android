package alvin.base.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import alvin.base.mvp.android.views.AndroidMainActivity;
import alvin.base.mvp.basic.views.BasicMainActivity;
import alvin.base.mvp.scope.ScopeMainActivity;
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
            R.id.btn_basic,
            R.id.btn_scope,
            R.id.btn_android
    })
    public void onButtonsClick(Button b) {
        Intent intent;

        switch (b.getId()) {
        case R.id.btn_basic:
            intent = new Intent(this, BasicMainActivity.class);
            break;
        case R.id.btn_scope:
            intent = new Intent(this, ScopeMainActivity.class);
            break;
        case R.id.btn_android:
            intent = new Intent(this, AndroidMainActivity.class);
            break;
        default:
            intent = null;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
