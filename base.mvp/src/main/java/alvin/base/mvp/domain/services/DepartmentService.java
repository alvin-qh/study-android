package alvin.base.mvp.domain.services;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Singleton;

import alvin.base.mvp.domain.models.Department;
import alvin.base.mvp.domain.repositories.DepartmentRepository;
import alvin.lib.common.dbflow.repositories.Transaction;
import alvin.lib.common.dbflow.repositories.TransactionManager;

@Singleton
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final TransactionManager txManager;

    @Inject
    public DepartmentService(@NonNull DepartmentRepository departmentRepository,
                             @NonNull TransactionManager txManager) {
        this.departmentRepository = departmentRepository;
        this.txManager = txManager;
    }

    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    public List<Department> saveAndGet(String departmentName) {
        try (Transaction tx = txManager.begin()) {
            Department department = new Department(departmentName);
            department.save();

            tx.commit();
        }

        return departmentRepository.findAll();
    }

    public List<Department> deleteAndGet(int departmentId) {
        try (Transaction tx = txManager.begin()) {
            Optional<Department> mayDept = departmentRepository.get(departmentId);
            mayDept.ifPresent(BaseModel::delete);

            tx.commit();
        }

        return departmentRepository.findAll();
    }
}
