package alvin.base.mvp.namecard.views;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import alvin.base.mvp.R;
import alvin.base.mvp.domain.models.NameCard;
import alvin.base.mvp.namecard.NameCardContracts;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.DaggerDialogFragment;

public class NameCardEditDialog extends DaggerDialogFragment implements
        NameCardContracts.EditView {

    private Unbinder unbinder;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        View view = inflater.inflate(R.layout.name_card_fragment_edit, container, false);
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

    public static NameCardEditDialog show(Fragment fragment, NameCard nameCard) {
        NameCardEditDialog dialog = new NameCardEditDialog();

        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGUMENT_NAME_CARD, nameCard);
        dialog.setArguments(bundle);

        dialog.show(fragment.getFragmentManager(), null);
        return dialog;
    }
}
