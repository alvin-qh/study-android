package alvin.ui.butterknife;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ButterKnifeFragment extends Fragment {

    private Unbinder unbinder;

    @BindViews({R.id.text_blue, R.id.text_red, R.id.text_green})
    List<TextView> textViews;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_butter_knife, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    public void setText(final String text) {
        final ButterKnife.Action<TextView> action = (view, index) -> view.setText(text);

        ButterKnife.apply(textViews, action);
    }
}
