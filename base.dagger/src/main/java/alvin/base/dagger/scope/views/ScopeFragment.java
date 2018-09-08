package alvin.base.dagger.scope.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import alvin.base.dagger.R;
import alvin.lib.mvp.contracts.adapters.FragmentAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static alvin.base.dagger.scope.ScopeContracts.FragmentPresenter;
import static alvin.base.dagger.scope.ScopeContracts.FragmentView;

public class ScopeFragment extends FragmentAdapter<FragmentPresenter> implements FragmentView {

    private Unbinder unbinder;

    @BindView(R.id.tv_activity_scope)
    TextView tvActivityScope;

    @BindView(R.id.tv_fragment_scope)
    TextView tvFragmentScope;

    private Integer backgroundColor;

    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater,
                                          @Nullable ViewGroup container,
                                          Bundle savedInstanceState) {
        final android.view.View view = inflater.inflate(R.layout.fragment_scope, container, false);
        unbinder = ButterKnife.bind(this, view);

        if (backgroundColor != null) {
            view.setBackgroundColor(backgroundColor);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.serviceName();
    }

    public void setBackground(int color) {
        final android.view.View view = getView();
        if (view != null) {
            view.setBackgroundColor(color);
        }
        backgroundColor = color;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void showActivityScopeService(String name) {
        tvActivityScope.setText(name);
    }

    @Override
    public void showFragmentScopeService(String name) {
        tvFragmentScope.setText(name);
    }

    @Override
    public boolean isDestroyed() {
        return isDetached();
    }
}
