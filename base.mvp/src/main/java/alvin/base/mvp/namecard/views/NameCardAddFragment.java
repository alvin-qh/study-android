package alvin.base.mvp.namecard.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import javax.inject.Inject;

import alvin.base.mvp.R;
import alvin.base.mvp.namecard.NameCardContracts;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.android.support.DaggerFragment;

public class NameCardAddFragment extends DaggerFragment
        implements NameCardContracts.AddView {

    @Inject NameCardContracts.AddPresenter presenter;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.name_card_fragment_add, container, false);
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

    @OnClick(R.id.btn_name_card_add)
    public void onNameCardAddButtonClick(Button b) {

    }

    public static NameCardAddFragment create() {
        return new NameCardAddFragment();
    }
}
