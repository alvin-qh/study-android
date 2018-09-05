package alvin.adv.mvp.department;

import java.util.List;

import alvin.adv.mvp.domain.models.Department;
import alvin.lib.mvp.contracts.IPresenter;
import alvin.lib.mvp.contracts.IView;

public interface DepartmentContracts {

    interface View extends IView {
        void showDepartments(List<Department> departments);
    }

    interface Presenter extends IPresenter {
        void loadDepartments();

        void saveDepartment(String departmentName);

        void deleteDepartment(int departmentId);
    }
}
