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
import alvin.base.mvp.scope.di.SubcomponentComponents;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SubcomponentFragment extends Fragment implements ScopeContract.FragmentView {

    private Unbinder unbinder;

    @BindView(R.id.tv_activity_scope)
    TextView tvActivityScope;

    @BindView(R.id.tv_fragment_scope)
    TextView tvFragmentScope;

    @Inject
    ScopeContract.FragmentPresenter presenter;

    private Integer backgroundColor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_scope, container, false);
        unbinder = ButterKnife.bind(this, view);

        final SubcomponentActivity activity = (SubcomponentActivity) getActivity();
        activity.component().subcomponentFragmentComponentBuilder()
                .fragmentModule(new SubcomponentComponents.SubcomponentFragmentModule(this))
                .build().inject(this);

        if (backgroundColor != null) {
            view.setBackgroundColor(backgroundColor);
        }

        return view;
    }

    public void setBackground(int color) {
        final View view = getView();
        if (view != null) {
            view.setBackgroundColor(color);
        }
        backgroundColor = color;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.started();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public Context context() {
        return getContext();
    }

    @Override
    public void showActivityScopeService(String name) {
        tvActivityScope.setText(name);
    }

    @Override
    public void showFragmentScopeService(String name) {
        tvFragmentScope.setText(name);
    }
}
