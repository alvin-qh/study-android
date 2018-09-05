package alvin.adv.database.main.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import alvin.adv.database.R;
import alvin.adv.database.dbflow.views.DBFlowActivity;
import alvin.adv.database.sqlite.views.SQLiteActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_sqlite)
    public void onSQLiteButtonClick(Button button) {
        Intent intent = new Intent(this, SQLiteActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_dbflow)
    public void onDBFlowButtonClick(Button button) {
        Intent intent = new Intent(this, DBFlowActivity.class);
        startActivity(intent);
    }
}
