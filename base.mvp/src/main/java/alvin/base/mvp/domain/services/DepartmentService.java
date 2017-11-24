package alvin.base.mvp.domain.services;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import alvin.base.mvp.domain.models.Department;
import alvin.base.mvp.domain.repositories.DepartmentRepository;
import alvin.lib.common.dbflow.repositories.TransactionManager;

@Singleton
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final TransactionManager transactionManager;

    @Inject
    public DepartmentService(@NonNull DepartmentRepository departmentRepository,
                             @NonNull TransactionManager transactionManager) {
        this.departmentRepository = departmentRepository;
        this.transactionManager = transactionManager;
    }

    public List<Department> findAll() {
        return departmentRepository.findAll();
    }
}
