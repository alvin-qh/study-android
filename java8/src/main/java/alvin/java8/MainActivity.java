package alvin.java8;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btnClickMe;
    private TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.textResult = (TextView) findViewById(R.id.text_result);

        this.btnClickMe = (Button) findViewById(R.id.btn_click_me);
        this.btnClickMe.setOnClickListener(view -> this.textResult.setText(R.string.text_hello));
    }
}
