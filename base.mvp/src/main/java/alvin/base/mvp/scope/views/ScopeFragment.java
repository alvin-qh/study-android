package alvin.base.mvp.scope.views;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import alvin.base.mvp.R;
import alvin.base.mvp.scope.ScopeContract;
import alvin.base.mvp.scope.di.DaggerScopeFragmentComponent;
import alvin.base.mvp.scope.di.ScopeFragmentModule;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ScopeFragment extends Fragment
        implements ScopeContract.ScopeFragmentView {

    private Unbinder unbinder;

    @BindView(R.id.tv_singleton_service)
    TextView tvSingletonService;

    @BindView(R.id.tv_session_service)
    TextView tvSessionService;

    @Inject
    ScopeContract.ScopeFragmentPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        final ScopeActivity activity = (ScopeActivity) getActivity();
        DaggerScopeFragmentComponent.builder()
                .scopeActivityComponent(activity.component())
                .scopeFragmentModule(new ScopeFragmentModule(this))
                .build().inject(this);

        final View view = inflater.inflate(R.layout.fragment_scope, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        presenter.started();
    }

    public void setBackground(int color) {
        final View view = getView();
        if (view != null) {
            view.setBackgroundColor(color);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public Context context() {
        return getContext();
    }

    @Override
    public void showSingletonService(String name) {
        tvSingletonService.setText(name);
    }

    @Override
    public void showSessionService(String name) {
        tvSessionService.setText(name);
    }
}
