package alvin.base.database.main.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import alvin.base.database.R;
import alvin.base.database.dbflow.views.DBFlowActivity;
import alvin.base.database.sqlite.views.SQLiteActivity;
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
