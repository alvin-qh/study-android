package alvin.base.mvp.namecard.presenter;

import android.annotation.SuppressLint;

import java.util.List;

import javax.inject.Inject;

import alvin.base.mvp.domain.models.Department;
import alvin.base.mvp.domain.services.DepartmentService;
import alvin.base.mvp.domain.services.NameCardService;
import alvin.base.mvp.namecard.NameCardContracts;
import alvin.lib.common.rx.RxDecorator;
import alvin.lib.common.rx.RxType;
import alvin.lib.mvp.contracts.adapters.PresenterAdapter;
import io.reactivex.Single;

public class NameCardEditPresenter
        extends PresenterAdapter<NameCardContracts.EditView>
        implements NameCardContracts.EditPresenter {

    private final NameCardService nameCardService;
    private final DepartmentService departmentService;
    private final RxDecorator.Builder rxDecoratorBuilder;

    @Inject
    public NameCardEditPresenter(NameCardContracts.EditView view,
                                 NameCardService nameCardService,
                                 DepartmentService departmentService,
                                 @RxType.IO RxDecorator.Builder rxDecoratorBuilder) {
        super(view);
        this.nameCardService = nameCardService;
        this.departmentService = departmentService;
        this.rxDecoratorBuilder = rxDecoratorBuilder;
    }

    @SuppressLint("CheckResult")
    @Override
    public void getDepartments() {
        final Single<List<Department>> single =
                Single.create(emitter -> emitter.onSuccess(departmentService.findAll()));

        final RxDecorator decorator = rxDecoratorBuilder.build();
        decorator.de(single).subscribe(departments ->
                with(editView -> editView.showDepartments(departments)));
    }
}
