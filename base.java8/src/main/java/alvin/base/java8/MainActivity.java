package alvin.base.java8;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.textResult = findViewById(R.id.text_result);

        Button btnClickMe = findViewById(R.id.btn_click_me);
        btnClickMe.setOnClickListener(view -> this.textResult.setText(R.string.string_hello_world));
    }
}
