package alvin.lib.common.dbflow.repositories;


import com.google.common.base.Preconditions;
import com.raizlabs.android.dbflow.sql.language.SQLOperator;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Where;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import androidx.annotation.NonNull;

public abstract class BaseRepository<T extends BaseModel> implements IRepository<T> {

    private final Class<T> entityClass;

    @SuppressWarnings("unchecked")
    protected BaseRepository() {
        ParameterizedType genericSuperclass = findParameterizedTypeParent(getClass());
        entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    private ParameterizedType findParameterizedTypeParent(Class clazz) {
        Type superClass = clazz.getGenericSuperclass();
        if (superClass instanceof ParameterizedType) {
            return (ParameterizedType) superClass;
        } else {
            Preconditions.checkNotNull(clazz.getSuperclass(),
                    "can not found ParameterizedType super class:  %s ", clazz);
            return findParameterizedTypeParent(clazz.getSuperclass());
        }
    }

    public Where<T> where(@NonNull final SQLOperator... conditions) {
        return SQLite.select().from(entityClass).where(conditions);
    }
}
