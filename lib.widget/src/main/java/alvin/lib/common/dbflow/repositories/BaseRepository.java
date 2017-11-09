package alvin.lib.common.dbflow.repositories;

import android.support.annotation.NonNull;

import com.google.common.base.Preconditions;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.sql.language.SQLOperator;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Where;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseRepository<T extends BaseModel> implements IRepository<T> {

    private final DatabaseDefinition database;
    private final Class<T> entityClass;

    public BaseRepository(@NonNull DatabaseDefinition database) {

        this.database = database;
        ParameterizedType genericSuperclass = findParameterizedTypeParent(getClass());

        //noinspection unchecked
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

    @NonNull
    public Transaction beginTransaction() {
        final DatabaseWrapper wrapper = this.database.getWritableDatabase();
        wrapper.beginTransaction();
        return new Transaction(wrapper);
    }

    public void executeTransaction(ITransaction trans) {
        database.executeTransaction(trans);
    }

    public void save(@NonNull T entity) {
        database.executeTransaction(db -> entity.save());
        entity.load();
    }

    public void update(@NonNull T entity) {
        database.executeTransaction(db -> entity.update());
    }

    public void delete(@NonNull T entity) {
        database.executeTransaction(db -> entity.delete());
    }

    public Where<T> where(@NonNull final SQLOperator... conditions) {
        return SQLite.select().from(entityClass).where(conditions);
    }
}
