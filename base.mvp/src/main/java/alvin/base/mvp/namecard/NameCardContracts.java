package alvin.base.mvp.namecard;

import java.util.List;

import alvin.base.mvp.domain.models.Department;
import alvin.lib.mvp.contracts.IPresenter;
import alvin.lib.mvp.contracts.IView;

public interface NameCardContracts {

    interface DisplayView extends IView {
    }

    interface DisplayPresenter extends IPresenter {
    }

    interface AddView extends IView {
    }

    interface AddPresenter extends IPresenter {
    }

    interface EditView extends IView {
        void showDepartments(List<Department> departments);
    }

    interface EditPresenter extends IPresenter {
        void getDepartments();
    }
}
