package alvin.base.mvp.department;

import java.util.List;

import alvin.base.mvp.domain.models.Department;
import alvin.lib.mvp.IPresenter;
import alvin.lib.mvp.IView;

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
