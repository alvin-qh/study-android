package alvin.base.mvp.department.presenters;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import alvin.base.mvp.department.DepartmentContracts;
import alvin.base.mvp.domain.models.Department;
import alvin.base.mvp.domain.services.DepartmentService;
import alvin.lib.common.rx.RxManager;
import alvin.lib.common.rx.SingleSubscriber;
import alvin.lib.mvp.ViewPresenterAdapter;

public class DepartmentPresenter extends ViewPresenterAdapter<DepartmentContracts.View>
        implements DepartmentContracts.Presenter {

    private final RxManager rxManager;
    private final DepartmentService departmentService;

    @Inject
    public DepartmentPresenter(@NonNull DepartmentContracts.View view,
                               @NonNull RxManager rxManager,
                               @NonNull DepartmentService departmentService) {
        super(view);
        this.rxManager = rxManager;
        this.departmentService = departmentService;
    }

    @Override
    public void loadDepartments() {
        SingleSubscriber<List<Department>> subscriber =
                rxManager.single(emitter ->
                        emitter.onSuccess(departmentService.findAll()));

        subscriber.subscribe(departments ->
                withView(view ->
                        view.showDepartments(departments)));
    }

    @Override
    public void saveDepartment(String departmentName) {
        SingleSubscriber<List<Department>> subscriber =
                rxManager.single(emitter ->
                        emitter.onSuccess(departmentService.saveAndGet(departmentName)));

        subscriber.subscribe(departments ->
                withView(view ->
                        view.showDepartments(departments)));
    }

    @Override
    public void deleteDepartment(int departmentId) {
        SingleSubscriber<List<Department>> subscriber =
                rxManager.single(emitter ->
                        emitter.onSuccess(departmentService.deleteAndGet(departmentId)));

        subscriber.subscribe(departments ->
                withView(view ->
                        view.showDepartments(departments)));
    }
}
