package alvin.base.database.dbflow.domain.repositories;

import com.raizlabs.android.dbflow.sql.language.Where;

import java.util.List;
import java.util.Optional;

import alvin.base.database.common.domain.models.Gender;
import alvin.base.database.dbflow.domain.models.Person;
import alvin.base.database.dbflow.domain.models.Person_Table;
import alvin.lib.common.dbflow.repositories.BaseRepository;

/**
 * @see BaseRepository for more details
 */
public class PersonDBFlowRepository extends BaseRepository<Person> {

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

//    public void delete(@NonNull int id) {
//      database.executeTransaction(db -> SQLite.delete().from(Person.class).where(Person_Table.id.eq(person.getId())));
//    }
}
