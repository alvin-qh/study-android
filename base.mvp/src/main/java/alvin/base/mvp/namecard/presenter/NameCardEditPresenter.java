package alvin.base.mvp.namecard.presenter;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import alvin.base.mvp.domain.models.Department;
import alvin.base.mvp.domain.services.DepartmentService;
import alvin.base.mvp.domain.services.NameCardService;
import alvin.base.mvp.namecard.NameCardContracts;
import alvin.lib.common.rx.RxDecorator;
import alvin.lib.mvp.contracts.adapters.PresenterAdapter;
import io.reactivex.Single;

public class NameCardEditPresenter
        extends PresenterAdapter<NameCardContracts.EditView>
        implements NameCardContracts.EditPresenter {

    private final NameCardService nameCardService;
    private final DepartmentService departmentService;
    private final RxDecorator rxDecorator;

    @Inject
    public NameCardEditPresenter(@NonNull NameCardContracts.EditView view,
                                 @NonNull NameCardService nameCardService,
                                 @NonNull DepartmentService departmentService,
                                 @NonNull RxDecorator rxDecorator) {
        super(view);
        this.nameCardService = nameCardService;
        this.departmentService = departmentService;
        this.rxDecorator = rxDecorator;
    }

    @Override
    public void getDepartments() {
        rxDecorator.<List<Department>>de(
                Single.create(emitter -> emitter.onSuccess(departmentService.findAll()))
        ).subscribe(departments ->
                with(editView -> editView.showDepartments(departments)));
    }
}
