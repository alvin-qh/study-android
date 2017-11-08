package alvin.base.mvp.basic.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

import alvin.base.mvp.R;
import alvin.base.mvp.basic.di.BasicMainActivityModule;
import alvin.base.mvp.basic.di.DaggerBasicMainActivityComponent;
import alvin.base.mvp.basic.domain.services.BasicService;
import butterknife.ButterKnife;

@Singleton
public class BasicMainActivity extends AppCompatActivity {

    @Inject
    BasicService service;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_basic_main);

        ButterKnife.bind(this);

        DaggerBasicMainActivityComponent.builder()
                .basicMainActivityModule(new BasicMainActivityModule(this))
                .build()
                .inject(this);
    }
}
