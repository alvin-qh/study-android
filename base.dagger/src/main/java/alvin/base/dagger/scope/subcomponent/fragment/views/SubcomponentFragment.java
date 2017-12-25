package alvin.base.dagger.scope.subcomponent.fragment.views;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import alvin.base.dagger.R;
import alvin.base.dagger.scope.subcomponent.fragment.FragmentContracts;
import alvin.base.dagger.scope.subcomponent.fragment.FragmentModule;
import alvin.base.dagger.scope.subcomponent.views.SubcomponentActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SubcomponentFragment
        extends Fragment
        implements FragmentContracts.View {

    private Unbinder unbinder;

    @BindView(R.id.tv_activity_scope)
    TextView tvActivityScope;

    @BindView(R.id.tv_fragment_scope)
    TextView tvFragmentScope;

    @Inject
    FragmentContracts.Presenter presenter;

    private Integer backgroundColor;

    @Nullable
    @Override
    public android.view.View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final android.view.View view = inflater.inflate(R.layout.scope_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        final SubcomponentActivity activity = (SubcomponentActivity) getActivity();
        activity.component().fragmentComponentBuilder()
                .fragmentModule(new FragmentModule(this))
                .build().inject(this);

        if (backgroundColor != null) {
            view.setBackgroundColor(backgroundColor);
        }

        return view;
    }

    public void setBackground(int color) {
        final android.view.View view = getView();
        if (view != null) {
            view.setBackgroundColor(color);
        }
        backgroundColor = color;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.serviceName();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
}
