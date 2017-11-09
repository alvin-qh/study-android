package alvin.base.mvp.scope.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import javax.inject.Inject;

import alvin.base.mvp.R;
import alvin.base.mvp.scope.ScopeContract;
import alvin.base.mvp.scope.di.DaggerScopeSingletonComponent;
import alvin.base.mvp.scope.di.ScopeSingletonComponent;
import alvin.base.mvp.scope.di.ScopeSingletonModule;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ScopeMainActivity extends AppCompatActivity implements ScopeContract.View {

    @BindView(R.id.tv_singleton_service)
    TextView tvSingletonService;

    @BindColor(R.color.color_blue)
    int colorBlue;

    @BindColor(R.color.color_green)
    int colorGreen;

    SessionFragment fragment1;

    SessionFragment fragment2;

    @Inject
    ScopeContract.Presenter presenter;

    private ScopeSingletonComponent singletonComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scope_main);

        ButterKnife.bind(this);

        fragment1 = (SessionFragment) getFragmentManager().findFragmentById(R.id.frg_session_1);
        fragment1.setBackground(colorBlue);

        fragment2 = (SessionFragment) getFragmentManager().findFragmentById(R.id.frg_session_2);
        fragment2.setBackground(colorGreen);

        singletonComponent = DaggerScopeSingletonComponent.builder()
                .scopeSingletonModule(new ScopeSingletonModule(this))
                .build();
        singletonComponent.inject(this);
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

    @Override
    public void showSingletonService(String name) {
        tvSingletonService.setText(name);
    }

    public ScopeSingletonComponent getSingletonComponent() {
        return singletonComponent;
    }
}
