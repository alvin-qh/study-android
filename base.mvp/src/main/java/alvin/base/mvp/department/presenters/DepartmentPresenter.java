package alvin.base.mvp.department.presenters;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import alvin.base.mvp.department.DepartmentContracts;
import alvin.base.mvp.domain.models.Department;
import alvin.base.mvp.domain.services.DepartmentService;
import alvin.lib.common.rx.RxDecorator;
import alvin.lib.mvp.contracts.adapters.PresenterAdapter;
import io.reactivex.Single;

public class DepartmentPresenter
        extends PresenterAdapter<DepartmentContracts.View>
        implements DepartmentContracts.Presenter {

    private final RxDecorator rxDecorator;
    private final DepartmentService departmentService;

    @Inject
    public DepartmentPresenter(@NonNull DepartmentContracts.View view,
                               @NonNull RxDecorator rxDecorator,
                               @NonNull DepartmentService departmentService) {
        super(view);
        this.rxDecorator = rxDecorator;
        this.departmentService = departmentService;
    }

    @Override
    public void loadDepartments() {
        rxDecorator.<List<Department>>de(
                Single.create(emitter ->
                        emitter.onSuccess(departmentService.findAll()))
        ).subscribe(departments ->
                with(view -> view.showDepartments(departments)));
    }

    @Override
    public void saveDepartment(String departmentName) {
        rxDecorator.<List<Department>>de(
                Single.create(emitter ->
                        emitter.onSuccess(departmentService.saveAndGet(departmentName)))
        ).subscribe(departments ->
                with(view -> view.showDepartments(departments)));
    }

    @Override
    public void deleteDepartment(int departmentId) {
        rxDecorator.<List<Department>>de(
                Single.create(emitter ->
                        emitter.onSuccess(departmentService.deleteAndGet(departmentId)))
        ).subscribe(departments ->
                with(view -> view.showDepartments(departments)));
    }
}
