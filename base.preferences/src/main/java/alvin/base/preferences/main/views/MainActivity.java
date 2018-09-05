package alvin.adv.preferences.main.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import alvin.adv.preferences.R;
import alvin.adv.preferences.original.views.OriginalActivity;
import alvin.adv.preferences.storebox.views.StoreBoxActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_preferences_original)
    public void onOriginalClick(Button b) {
        startActivity(new Intent(this, OriginalActivity.class));
    }

    @OnClick(R.id.btn_preferences_storebox)
    public void onStoreBoxClick(Button b) {
        startActivity(new Intent(this, StoreBoxActivity.class));
    }
}
