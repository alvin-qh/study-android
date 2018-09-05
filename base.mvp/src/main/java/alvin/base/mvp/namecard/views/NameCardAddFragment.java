package alvin.adv.mvp.namecard.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import alvin.adv.mvp.R;
import alvin.adv.mvp.domain.models.NameCard;
import alvin.adv.mvp.namecard.NameCardContracts;
import alvin.lib.mvp.contracts.adapters.FragmentAdapter;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class NameCardAddFragment
        extends FragmentAdapter<NameCardContracts.AddPresenter>
        implements NameCardContracts.AddView {

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
    public void onNameCardAddButtonClick(ImageButton b) {
        NameCardEditDialog dialog = NameCardEditDialog.show(this, new NameCard());
        dialog.setOnOKClickListener(view -> {
            NameCard nameCard = dialog.getNameCard();
        });
    }

    public static NameCardAddFragment create() {
        return new NameCardAddFragment();
    }
}
