package alvin.adv.mvp.namecard.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.google.common.base.Preconditions;

import java.util.Collections;
import java.util.List;

import alvin.adv.mvp.R;
import alvin.adv.mvp.domain.models.Department;
import alvin.adv.mvp.domain.models.NameCard;
import alvin.adv.mvp.namecard.NameCardContracts;
import alvin.lib.common.collect.Collections2;
import alvin.lib.mvp.contracts.adapters.DialogFragmentAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class NameCardEditDialog
        extends DialogFragmentAdapter<NameCardContracts.EditPresenter>
        implements NameCardContracts.EditView {

    private static final String ARGUMENT_NAME_CARD = "name_card";

    private Unbinder unbinder;
    private NameCard nameCard;

    private View.OnClickListener onOKClickListener;
    private View.OnClickListener onCancelClickListener;

    @BindView(R.id.et_name) EditText etName;
    @BindView(R.id.sp_department) Spinner spDepartment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.AppTheme_Dialog);

        Bundle arguments = getArguments();
        if (arguments != null) {
            nameCard = arguments.getParcelable(ARGUMENT_NAME_CARD);
        }
        Preconditions.checkNotNull(nameCard, "nameCard != null");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.name_card_fragment_edit, container, false);
        unbinder = ButterKnife.bind(this, view);

        etName.setText(nameCard.getName());
        presenter.getDepartments();
        return view;
    }

    @Override
    public void showDepartments(List<Department> departments) {
        Department nonSelect = new Department(0, getString(R.string.text_nothing));
        departments = Collections2.merge(Collections.singletonList(nonSelect), departments);

        SpinnerAdapter adapter = new ArrayAdapter<>(getContext(),
                R.layout.support_simple_spinner_dropdown_item, departments);
        spDepartment.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @OnClick(R.id.btn_confirm)
    public void onConfirmButtonClick(Button b) {
        nameCard.setName(etName.getText().toString());

        if (onOKClickListener != null) {
            onOKClickListener.onClick(b);
        }
        dismiss();
    }

    @OnClick(R.id.btn_cancel)
    public void onCancelButtonClick(Button b) {
        if (onCancelClickListener != null) {
            onCancelClickListener.onClick(b);
        }
        dismiss();
    }

    public NameCard getNameCard() {
        return nameCard;
    }

    public void setOnOKClickListener(View.OnClickListener onOKClickListener) {
        this.onOKClickListener = onOKClickListener;
    }

    public void setOnCancelClickListener(View.OnClickListener onCancelClickListener) {
        this.onCancelClickListener = onCancelClickListener;
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
