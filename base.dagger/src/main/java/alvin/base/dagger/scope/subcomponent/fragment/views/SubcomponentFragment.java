package alvin.base.dagger.scope.subcomponent.fragment.views;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import alvin.base.dagger.R;
import alvin.base.dagger.scope.subcomponent.fragment.FragmentModule;
import alvin.base.dagger.scope.subcomponent.views.SubcomponentActivity;
import alvin.base.dagger.scope.subcomponent.fragment.FragmentContracts;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SubcomponentFragment extends Fragment implements FragmentContracts.View {

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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.scope_fragment, container, false);
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
        final View view = getView();
        if (view != null) {
            view.setBackgroundColor(color);
        }
        backgroundColor = color;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
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
