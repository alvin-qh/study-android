package alvin.base.mvp.scope.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import javax.inject.Inject;

import alvin.base.mvp.R;
import alvin.base.mvp.scope.ScopeContract;
import alvin.base.mvp.scope.di.DaggerScopeActivityComponent;
import alvin.base.mvp.scope.di.ScopeActivityComponent;
import alvin.base.mvp.scope.di.ScopeActivityModule;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ScopeActivity extends AppCompatActivity
        implements ScopeContract.ScopeActivityView {

    @BindView(R.id.tv_singleton_service)
    TextView tvSingletonService;

    @BindColor(R.color.color_blue)
    int colorBlue;

    @BindColor(R.color.color_green)
    int colorGreen;

    ScopeFragment fragment1;

    ScopeFragment fragment2;

    @Inject
    ScopeContract.ScopeActivityPresenter presenter;

    private ScopeActivityComponent component;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        component = DaggerScopeActivityComponent.builder()
                .scopeActivityModule(new ScopeActivityModule(this))
                .build();
        component.inject(this);

        setContentView(R.layout.activity_scope);
        ButterKnife.bind(this);

        fragment1 = (ScopeFragment) getFragmentManager().findFragmentById(R.id.frg_session_1);
        fragment1.setBackground(colorBlue);

        fragment2 = (ScopeFragment) getFragmentManager().findFragmentById(R.id.frg_session_2);
        fragment2.setBackground(colorGreen);
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

    public ScopeActivityComponent component() {
        return component;
    }
}
