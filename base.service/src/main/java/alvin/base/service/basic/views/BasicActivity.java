package alvin.base.service.basic.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import alvin.base.service.basic.BasicContracts;
import dagger.android.AndroidInjection;

public class BasicActivity extends AppCompatActivity implements BasicContracts.View {

    @Inject BasicContracts.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidInjection.inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        presenter.started();
    }

    @Override
    public Context context() {
        return this;
    }
}
