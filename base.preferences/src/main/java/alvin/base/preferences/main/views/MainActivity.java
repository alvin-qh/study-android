package alvin.base.preferences.main.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import alvin.base.preferences.R;
import alvin.base.preferences.original.views.OriginalActivity;
import alvin.base.preferences.storebox.views.StoreBoxActivity;
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

    @OnClick(R.id.btn_preferences_original)
    public void onOriginalClick(Button b) {
        startActivity(new Intent(this, OriginalActivity.class));
    }

    @OnClick(R.id.btn_preferences_storebox)
    public void onStoreBoxClick(Button b) {
        startActivity(new Intent(this, StoreBoxActivity.class));
    }
}
