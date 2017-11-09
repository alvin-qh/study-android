package alvin.base.mvp.scope.views;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import alvin.base.mvp.R;
import alvin.base.mvp.scope.ScopeContract;
import alvin.base.mvp.scope.di.DaggerScopeSessionComponent;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SessionFragment extends Fragment implements ScopeContract.View {

    private Unbinder unbinder;

    @BindViews(R.id.tv_session_service)
    List<TextView> tvSessionService;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_session, container, false);
        unbinder = ButterKnife.bind(this, this.view);

        final ScopeMainActivity activity = (ScopeMainActivity) getActivity();
        DaggerScopeSessionComponent.builder()
                .scopeSingletonComponent(activity.getSingletonComponent())
                .build().inject(this);

        return this.view;
    }

    public void setBackground(int color) {
        this.view.setBackgroundColor(color);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
        this.view = null;
    }

    @Override
    public Context context() {
        return getContext();
    }

    @Override
    public void showSingletonService(String name) {
        
    }
}
