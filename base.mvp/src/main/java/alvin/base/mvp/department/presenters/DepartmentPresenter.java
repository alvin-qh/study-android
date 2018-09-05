package alvin.base.mvp.department.presenters;

import android.annotation.SuppressLint;

import java.util.List;

import javax.inject.Inject;

import alvin.base.mvp.department.DepartmentContracts;
import alvin.base.mvp.domain.models.Department;
import alvin.base.mvp.domain.services.DepartmentService;
import alvin.lib.common.rx.RxDecorator;
import alvin.lib.common.rx.RxType;
import alvin.lib.mvp.contracts.adapters.PresenterAdapter;
import io.reactivex.Single;

public class DepartmentPresenter
        extends PresenterAdapter<DepartmentContracts.View>
        implements DepartmentContracts.Presenter {

    private final RxDecorator.Builder rxDecoratorBuilder;
    private final DepartmentService departmentService;

    @Inject
    public DepartmentPresenter(DepartmentContracts.View view,
                               @RxType.IO RxDecorator.Builder rxDecoratorBuilder,
                               DepartmentService departmentService) {
        super(view);
        this.rxDecoratorBuilder = rxDecoratorBuilder;
        this.departmentService = departmentService;
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadDepartments() {
        final Single<List<Department>> single =
                Single.create(emitter -> emitter.onSuccess(departmentService.findAll()));

        final RxDecorator decorator = rxDecoratorBuilder.build();
        decorator.de(single).subscribe(departments -> with(view -> view.showDepartments(departments)));
    }

    @SuppressLint("CheckResult")
    @Override
    public void saveDepartment(String departmentName) {
        final Single<List<Department>> single =
                Single.create(emitter -> emitter.onSuccess(departmentService.saveAndGet(departmentName)));

        final RxDecorator decorator = rxDecoratorBuilder.build();
        decorator.de(single).subscribe(departments -> with(view -> view.showDepartments(departments)));
    }

    @SuppressLint("CheckResult")
    @Override
    public void deleteDepartment(int departmentId) {
        final Single<List<Department>> single = Single.create(emitter ->
                emitter.onSuccess(departmentService.deleteAndGet(departmentId)));

        final RxDecorator decorator = rxDecoratorBuilder.build();
        decorator.de(single).subscribe(departments -> with(view -> view.showDepartments(departments)));
    }
}
