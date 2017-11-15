package alvin.base.mvp.main.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import alvin.base.mvp.R;
import alvin.base.mvp.android.views.AndroidActivity;
import alvin.base.mvp.basic.views.BasicActivity;
import alvin.base.mvp.multibindings.views.MultibindingsActivity;
import alvin.base.mvp.scope.views.ScopeActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        ButterKnife.bind(this);
    }

    @OnClick({
            R.id.btn_basic,
            R.id.btn_scope,
            R.id.btn_android,
            R.id.btn_multibindings
    })
    public void onButtonsClick(Button b) {
        Intent intent;

        switch (b.getId()) {
        case R.id.btn_basic:
            intent = new Intent(this, BasicActivity.class);
            break;
        case R.id.btn_scope:
            intent = new Intent(this, ScopeActivity.class);
            break;
        case R.id.btn_android:
            intent = new Intent(this, AndroidActivity.class);
            break;
        case R.id.btn_multibindings:
            intent = new Intent(this, MultibindingsActivity.class);
            break;
        default:
            intent = null;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}