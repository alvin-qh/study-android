package alvin.adv.mvp.department.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import alvin.adv.mvp.R;
import alvin.adv.mvp.department.DepartmentContracts;
import alvin.adv.mvp.domain.models.Department;
import alvin.lib.common.utils.SystemServices;
import alvin.lib.mvp.contracts.adapters.DialogFragmentAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DepartmentEditDialog
        extends DialogFragmentAdapter<DepartmentContracts.Presenter>
        implements DepartmentContracts.View {

    @Inject SystemServices systemServices;

    @BindView(R.id.et_department_name) TextView tvDepartmentName;
    @BindView(R.id.rv_department_list) RecyclerView rvDepartmentList;

    private DepartmentListAdapter listAdapter;
    private Unbinder unbinder;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AppCompatDialog(getContext(), getTheme()) {
            @Override
            public boolean dispatchTouchEvent(@NonNull MotionEvent ev) {
                final android.view.View focusView = getCurrentFocus();
                if (focusView != null && canInputHidden(focusView, ev)) {
                    systemServices.getInputMethodManager()
                            .hideSoftInputFromWindow(focusView.getWindowToken(), 0);
                }
                return super.dispatchTouchEvent(ev);
            }

            private boolean canInputHidden(android.view.View focusView, MotionEvent ev) {
                if (focusView instanceof EditText) {
                    final int[] locations = {0, 0};
                    focusView.getLocationInWindow(locations);

                    final RectF rectF = new RectF(
                            locations[0],
                            locations[1],
                            locations[0] + focusView.getWidth(),
                            locations[1] + focusView.getHeight());

                    return !rectF.contains(ev.getX(), ev.getY());
                }
                return false;
            }
        };
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.AppTheme_Dialog);

        final Context context = getContext();
        if (context != null) {
            listAdapter = new DepartmentListAdapter(context, Collections.emptyList());
            listAdapter.setDeleteDepartmentListener(departmentId ->
                    presenter.deleteDepartment(departmentId));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.loadDepartments();
    }

    @Nullable
    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater,
                                          @Nullable ViewGroup container,
                                          @Nullable Bundle savedInstanceState) {
        final android.view.View view = inflater.inflate(R.layout.department_fragment_edit, container);
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
