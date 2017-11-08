package alvin.base.mvp.android.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import alvin.base.mvp.R;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class AndroidMainActivity extends AppCompatActivity {

    @Inject
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_basic_main);

        ButterKnife.bind(this);

        AndroidInjection.inject(this);
    }
}
