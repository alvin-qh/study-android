package alvin.base.mvp.domain.repositories;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Singleton;

import alvin.base.mvp.domain.models.Department;
import alvin.base.mvp.domain.models.Department_Table;
import alvin.lib.common.collect.Collections2;
import alvin.lib.common.dbflow.repositories.BaseRepository;

@Singleton
public class DepartmentRepository extends BaseRepository<Department> {

    @Inject
    public DepartmentRepository() { }

    public List<Department> findAll() {
        return where().orderBy(Department_Table.id.desc()).queryList();
    }

    public Optional<Department> get(int id) {
        return Collections2.first(where(Department_Table.id.eq(id)).queryList());
    }
}
