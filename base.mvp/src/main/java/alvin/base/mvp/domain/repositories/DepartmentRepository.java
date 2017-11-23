package alvin.base.mvp.domain.repositories;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;

import javax.inject.Inject;
import javax.inject.Singleton;

import alvin.base.mvp.domain.models.Department;
import alvin.lib.common.dbflow.repositories.BaseRepository;

@Singleton
public class DepartmentRepository extends BaseRepository<Department> {

    @Inject
    public DepartmentRepository(@NonNull DatabaseDefinition database) {
        super();
    }
}
