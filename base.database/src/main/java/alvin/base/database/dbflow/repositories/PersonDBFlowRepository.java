package alvin.base.database.dbflow.repositories;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.sql.language.Where;

import java.util.List;
import java.util.Optional;

import alvin.base.database.dbflow.models.Person;
import alvin.base.database.dbflow.models.Person_Table;
import alvin.base.database.models.Gender;
import alvin.lib.common.dbflow.repositories.BaseRepository;

/**
 * @see BaseRepository for more details
 */
public class PersonDBFlowRepository extends BaseRepository<Person> {

    public PersonDBFlowRepository(@NonNull DatabaseDefinition database) {
        super(database);
    }

    public Optional<Person> findById(int id) {
        return Optional.ofNullable(where(Person_Table.id.eq(id)).querySingle());
    }

    public List<Person> findByGender(Gender gender) {
        Where<Person> where = where();
        if (gender != null) {
            where = where.and(Person_Table.gender.eq(gender));
        }
        return where.queryList();
    }

    @Override
    public void delete(@NonNull Person person) {
        super.delete(person);
//      database.executeTransaction(db -> SQLite.delete().from(Person.class).where(Person_Table.id.eq(person.getId())));
    }
}
