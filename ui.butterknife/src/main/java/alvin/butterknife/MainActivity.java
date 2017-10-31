package alvin.butterknife;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.text_main)
    TextView tvMain;

    @BindString(R.string.string_hello_world)
    String hello;

    @BindColor(R.color.colorAccent)
    int colorAccent;

    @BindDimen(R.dimen.large_font_size)
    float dimenFontSize;

    ButterKnifeFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.fragment = (ButterKnifeFragment) getFragmentManager().findFragmentById(R.id.frag_butter_knife);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_hello)
    public void onHelloClick(Button element) {
        tvMain.setText(hello);
        tvMain.setTextColor(colorAccent);
        tvMain.setTextSize(dimenFontSize);

        this.fragment.setText(hello);

        element.setEnabled(false);
    }
}
