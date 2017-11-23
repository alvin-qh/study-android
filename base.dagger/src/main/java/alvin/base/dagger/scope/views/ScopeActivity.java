package alvin.base.dagger.scope.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import alvin.base.dagger.R;
import alvin.base.dagger.scope.dependency.views.DependencyActivity;
import alvin.base.dagger.scope.subcomponent.views.SubcomponentActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScopeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.scope_activity);

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
