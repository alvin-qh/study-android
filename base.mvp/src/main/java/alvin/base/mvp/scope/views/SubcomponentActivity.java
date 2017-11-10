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
import alvin.base.mvp.scope.di.DaggerSubcomponentComponents_SubcomponentActivityComponent;
import alvin.base.mvp.scope.di.SubcomponentComponents;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SubcomponentActivity extends AppCompatActivity implements ScopeContract.ActivityView {

    @BindView(R.id.tv_activity_scope)
    TextView tvSingletonService;

    @BindColor(R.color.color_blue)
    int colorBlue;

    @BindColor(R.color.color_green)
    int colorGreen;

    @Inject
    ScopeContract.ActivityPresenter presenter;

    private SubcomponentComponents.SubcomponentActivityComponent component;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        component = DaggerSubcomponentComponents_SubcomponentActivityComponent.builder()
                .subcomponentActivityModule(new SubcomponentComponents.SubcomponentActivityModule(this))
                .build();
        component.inject(this);

        setContentView(R.layout.activity_scope);
        ButterKnife.bind(this);

        bindFragments();
    }

    private void bindFragments() {
        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();

        SubcomponentFragment fragment = new SubcomponentFragment();
        fragment.setBackground(colorBlue);
        transaction.replace(R.id.frg_scope_1, fragment);

        fragment = new SubcomponentFragment();
        fragment.setBackground(colorGreen);
        transaction.replace(R.id.frg_scope_2, fragment);

        transaction.commit();
    }

    @Override
    public Context context() {
        return this;
    }

    SubcomponentComponents.SubcomponentActivityComponent component() {
        return this.component;
    }

    @Override
    public void showActivityScopeService(String name) {
    }
}
