package alvin.base.mvp.namecard.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.base.Preconditions;

import javax.inject.Inject;

import alvin.base.mvp.R;
import alvin.base.mvp.domain.models.NameCard;
import alvin.base.mvp.namecard.NameCardContracts;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerFragment;

public class NameCardDisplayFragment extends DaggerFragment
        implements NameCardContracts.DisplayView {

    private static final String ARGUMENT_NAME_CARD = "name_card";

    @Inject NameCardContracts.DisplayPresenter presenter;

    private Unbinder unbinder;
    private NameCard nameCard;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle arguments = getArguments();
        if (arguments != null) {
            nameCard = arguments.getParcelable(ARGUMENT_NAME_CARD);
        }
        Preconditions.checkNotNull(nameCard, "nameCard != null");

        presenter.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.name_card_fragment_display, container, false);
        unbinder = ButterKnife.bind(this, view);

        showNameCard();
        return view;
    }

    private void showNameCard() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    public static NameCardDisplayFragment create(NameCard nameCard) {
        NameCardDisplayFragment fragment = new NameCardDisplayFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGUMENT_NAME_CARD, nameCard);
        fragment.setArguments(bundle);

        return fragment;
    }
}
