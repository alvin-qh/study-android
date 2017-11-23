package alvin.base.dagger.scope.dependency.views;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import javax.inject.Inject;

import alvin.base.dagger.R;
import alvin.base.dagger.scope.dependency.DaggerDependencyComponent;
import alvin.base.dagger.scope.dependency.DependencyComponent;
import alvin.base.dagger.scope.dependency.DependencyContracts;
import alvin.base.dagger.scope.dependency.DependencyModule;
import alvin.base.dagger.scope.dependency.fragment.views.DependencyFragment;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DependencyActivity extends AppCompatActivity implements DependencyContracts.View {

    @BindView(R.id.tv_activity_scope)
    TextView tvSingletonService;

    @BindColor(R.color.color_blue)
    int colorBlue;

    @BindColor(R.color.color_green)
    int colorGreen;

    @Inject
    DependencyContracts.Presenter presenter;

    private DependencyComponent component;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        component = DaggerDependencyComponent.builder()
                .dependencyModule(new DependencyModule(this))
                .build();
        component.inject(this);

        setContentView(R.layout.scope_activity_common);
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
        presenter.onStart();
    }

    @Override
    public void showActivityScopeService(String name) {
        tvSingletonService.setText(name);
    }

    public DependencyComponent component() {
        return component;
    }
}
