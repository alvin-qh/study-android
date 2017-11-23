package alvin.base.dagger.android.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import alvin.base.dagger.R;
import alvin.base.dagger.android.contributes.views.ContributesActivity;
import alvin.base.dagger.android.subcomponent.views.SubcomponentActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AndroidActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.android_activity);
        ButterKnife.bind(this);
    }

    @OnClick({
            R.id.btn_subcomponent,
            R.id.btn_contributes
    })
    public void onButtonsClick(Button b) {
        Intent intent;

        switch (b.getId()) {
        case R.id.btn_subcomponent:
            intent = new Intent(this, SubcomponentActivity.class);
            break;
        case R.id.btn_contributes:
            intent = new Intent(this, ContributesActivity.class);
            break;
        default:
            intent = null;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
