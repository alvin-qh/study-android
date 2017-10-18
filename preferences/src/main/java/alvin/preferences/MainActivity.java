package alvin.preferences;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

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
    public void onOriginalClick(Button elem) {
        startActivity(new Intent(this, OriginalActivity.class));
    }

    @OnClick(R.id.btn_preferences_storebox)
    public void onStoreBoxClick(Button elem) {
        startActivity(new Intent(this, StoreBoxActivity.class));
    }
}
