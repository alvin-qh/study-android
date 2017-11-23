package alvin.base.mvp.main.views;

import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import alvin.base.mvp.R;
import alvin.base.mvp.main.MainConstracts;
import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity
        implements MainConstracts.View {

    @Inject MainConstracts.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);

        presenter.onCreate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        presenter.onDestroy();
    }
}
