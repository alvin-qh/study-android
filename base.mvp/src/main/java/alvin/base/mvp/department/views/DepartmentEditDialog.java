package alvin.base.mvp.department.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import alvin.base.mvp.R;
import alvin.base.mvp.department.DepartmentContracts;
import alvin.base.mvp.domain.models.Department;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatDialogFragment;

public class DepartmentEditDialog extends DaggerAppCompatDialogFragment
        implements DepartmentContracts.View {

    @Inject DepartmentContracts.Presenter presenter;

    @BindView(R.id.et_department_name) TextView tvDepartmentName;
    @BindView(R.id.rv_department_list) RecyclerView rvDepartmentList;

    private DepartmentListAdapter listAdapter;
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.AppTheme_Dialog);

        listAdapter = new DepartmentListAdapter(getContext(), Collections.emptyList());
        listAdapter.setDeleteDepartmentListener(departmentId ->
                presenter.deleteDepartment(departmentId));
        presenter.onCreate();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.loadDepartments();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.department_fragment_edit, container);
        unbinder = ButterKnife.bind(this, view);

        rvDepartmentList.setAdapter(listAdapter);
        rvDepartmentList.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvDepartmentList.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (unbinder != null) {
            unbinder.unbind();
        }
        presenter.onDestroy();
    }

    @Override
    public void showDepartments(List<Department> departments) {
        listAdapter.update(departments);
    }

    @OnClick(R.id.btn_cancel)
    public void onCancelButtonClick(Button b) {
        getDialog().dismiss();
    }

    @OnClick(R.id.btn_department_add)
    public void onAddButtonClick(ImageButton b) {
        final String departmentName = tvDepartmentName.getText().toString().trim();
        if (!departmentName.isEmpty()) {
            presenter.saveDepartment(departmentName);
        }
    }

    public static DepartmentEditDialog show(AppCompatActivity activity) {
        DepartmentEditDialog dialog = new DepartmentEditDialog();
        dialog.show(activity.getSupportFragmentManager(), null);
        return dialog;
    }
}
