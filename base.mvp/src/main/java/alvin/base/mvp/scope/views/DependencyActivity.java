package alvin.base.mvp.scope.views;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import javax.inject.Inject;

import alvin.base.mvp.R;
import alvin.base.mvp.scope.ScopeContract;
import alvin.base.mvp.scope.di.DaggerDependencyComponents_DependencyActivityComponent;
import alvin.base.mvp.scope.di.DependencyComponents;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DependencyActivity extends AppCompatActivity implements ScopeContract.ActivityView {

    @BindView(R.id.tv_activity_scope)
    TextView tvSingletonService;

    @BindColor(R.color.color_blue)
    int colorBlue;

    @BindColor(R.color.color_green)
    int colorGreen;

    @Inject
    ScopeContract.ActivityPresenter presenter;

    private DependencyComponents.DependencyActivityComponent component;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        component = DaggerDependencyComponents_DependencyActivityComponent.builder()
                .dependencyActivityModule(new DependencyComponents.DependencyActivityModule(this))
                .build();
        component.inject(this);

        setContentView(R.layout.activity_scope);
        ButterKnife.bind(this);

        bindFragments();
    }

    private void bindFragments() {
        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();

        DependencyFragment fragment = new DependencyFragment();
        fragment.setBackground(colorBlue);
        transaction.replace(R.id.frg_scope_1, fragment);

        fragment = new DependencyFragment();
        fragment.setBackground(colorGreen);
        transaction.replace(R.id.frg_scope_2, fragment);

        transaction.commit();
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
    public void showActivityScopeService(String name) {
        tvSingletonService.setText(name);
    }

    public DependencyComponents.DependencyActivityComponent component() {
        return component;
    }
}
