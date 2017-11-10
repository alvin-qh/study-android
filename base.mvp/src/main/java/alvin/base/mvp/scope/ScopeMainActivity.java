package alvin.base.mvp.scope;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import alvin.base.mvp.R;
import alvin.base.mvp.scope.views.DependencyActivity;
import alvin.base.mvp.scope.views.SubcomponentActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScopeMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scope_main);

        ButterKnife.bind(this);
    }

    @OnClick({
            R.id.btn_dependency,
            R.id.btn_subcomponent
    })
    public void onButtonsClick(Button b) {
        Intent intent;

        switch (b.getId()) {
        case R.id.btn_dependency:
            intent = new Intent(this, DependencyActivity.class);
            break;
        case R.id.btn_subcomponent:
            intent = new Intent(this, SubcomponentActivity.class);
            break;
        default:
            intent = null;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
