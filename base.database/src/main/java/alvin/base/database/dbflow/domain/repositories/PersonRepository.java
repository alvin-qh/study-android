package alvin.base.database.dbflow.domain.repositories;

import com.raizlabs.android.dbflow.sql.language.Where;
import com.raizlabs.android.dbflow.sql.queriable.AsyncQuery;

import javax.inject.Inject;
import javax.inject.Singleton;

import alvin.base.database.common.domain.models.Gender;
import alvin.base.database.dbflow.domain.models.Person;
import alvin.base.database.dbflow.domain.models.Person_Table;
import alvin.lib.common.dbflow.repositories.BaseRepository;

/**
 * @see BaseRepository for more details
 */
@Singleton
public class PersonRepository extends BaseRepository<Person> {

    @Inject
    public PersonRepository() {
    }

    public AsyncQuery<Person> findById(int id) {
        return where(Person_Table.id.eq(id)).async();
    }

    public AsyncQuery<Person> findByGender(Gender gender) {
        Where<Person> where = where();
        if (gender != null) {
            where = where.and(Person_Table.gender.eq(gender));
        }
        return where.async();
    }

//    public void delete(@NonNull int id) {
//      database.executeTransaction(db -> SQLite.delete().from(Person.class).where(Person_Table.id.eq(person.getId())));
//    }
}
