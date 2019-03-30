package alvin.base.dagger.main.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import alvin.base.dagger.R;
import alvin.base.dagger.basic.views.BasicActivity;
import alvin.base.dagger.multibindings.views.MultibindingsActivity;
import alvin.base.dagger.scope.views.ScopeActivity;
import androidx.appcompat.app.AppCompatActivity;
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
