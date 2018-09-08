package alvin.base.dagger.scope.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import alvin.base.dagger.R;
import alvin.lib.mvp.contracts.adapters.ActivityAdapter;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

import static alvin.base.dagger.scope.ScopeContracts.ActivityPresenter;
import static alvin.base.dagger.scope.ScopeContracts.ActivityView;

public class ScopeActivity extends ActivityAdapter<ActivityPresenter> implements ActivityView {

    @BindView(R.id.tv_activity_scope)
    TextView tvSingletonService;

    @BindColor(R.color.color_blue)
    int colorBlue;

    @BindColor(R.color.color_green)
    int colorGreen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scope);
        ButterKnife.bind(this);

        bindFragments();
    }

    private void bindFragments() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();

        ScopeFragment fragment = new ScopeFragment();
        fragment.setBackground(colorBlue);
        transaction.replace(R.id.frg_scope_1, fragment);

        fragment = new ScopeFragment();
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
