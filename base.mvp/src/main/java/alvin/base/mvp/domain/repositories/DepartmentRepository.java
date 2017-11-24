package alvin.base.mvp.domain.repositories;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import alvin.base.mvp.domain.models.Department;
import alvin.lib.common.dbflow.repositories.BaseRepository;

@Singleton
public class DepartmentRepository extends BaseRepository<Department> {

    @Inject
    public DepartmentRepository() { }

    public List<Department> findAll() {
        return where().queryList();
    }
}
