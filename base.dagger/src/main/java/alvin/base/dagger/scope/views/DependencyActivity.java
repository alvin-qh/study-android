package alvin.base.dagger.scope.views;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import javax.inject.Inject;

import alvin.adv.dagger.scope.dependency.fragment.views.DependencyFragment;
import alvin.base.dagger.R;
import alvin.base.dagger.scope.ScopeContracts;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class DependencyActivity extends DaggerAppCompatActivity implements ScopeContracts.View {

    @BindView(R.id.tv_activity_scope)
    TextView tvSingletonService;

    @BindColor(R.color.color_blue)
    int colorBlue;

    @BindColor(R.color.color_green)
    int colorGreen;

    @Inject
    ScopeContracts.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
    protected void onResume() {
        super.onResume();
        presenter.serviceName();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void showActivityScopeService(String name) {
        tvSingletonService.setText(name);
    }
}
