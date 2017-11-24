package alvin.base.mvp.namecard.presenter;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import alvin.base.mvp.domain.models.Department;
import alvin.base.mvp.domain.services.DepartmentService;
import alvin.base.mvp.domain.services.NameCardService;
import alvin.base.mvp.namecard.NameCardContracts;
import alvin.lib.common.rx.RxManager;
import alvin.lib.common.rx.SingleSubscriber;
import alvin.lib.mvp.ViewPresenterAdapter;

public class NameCardEditPresenter extends ViewPresenterAdapter<NameCardContracts.EditView>
        implements NameCardContracts.EditPresenter {

    private final NameCardService nameCardService;
    private final DepartmentService departmentService;
    private final RxManager rxManager;

    @Inject
    public NameCardEditPresenter(@NonNull NameCardContracts.EditView view,
                                 @NonNull NameCardService nameCardService,
                                 @NonNull DepartmentService departmentService,
                                 @NonNull RxManager rxManager) {
        super(view);
        this.nameCardService = nameCardService;
        this.departmentService = departmentService;
        this.rxManager = rxManager;
    }

    @Override
    public void getDepartments() {
        SingleSubscriber<List<Department>> subscriber =
                rxManager.single(emitter -> {
                    emitter.onSuccess(departmentService.findAll());
                });
        subscriber.subscribe(departments ->
                withView(editView ->
                        editView.showDepartments(departments)
                )
        );
    }
}
