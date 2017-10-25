package alvin.database.dbflow.repositories;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Where;

import java.util.List;
import java.util.Optional;

import alvin.database.dbflow.models.Person;
import alvin.database.dbflow.models.Person_Table;
import alvin.database.models.Gender;

public class PersonDBFlowRepository {
    private final DatabaseDefinition database;

    public PersonDBFlowRepository(DatabaseDefinition database) {
        this.database = database;
    }

    public Optional<Person> findById(int id) {
        Person person = SQLite.select().from(Person.class).where(Person_Table.id.eq(id)).querySingle();
        return Optional.ofNullable(person);
    }

    public List<Person> findByGender(Gender gender) {
        Where<Person> where = SQLite.select().from(Person.class).where();
        if (gender != null) {
            where = where.and(Person_Table.gender.eq(gender));
        }
        return where.queryList();
    }

    public void save(Person person) {
        database.executeTransaction(db -> person.save());
    }

    public void update(Person person) {
        database.executeTransaction(db -> person.update());
    }

    public void delete(Person person) {
//        database.executeTransaction(db -> SQLite.delete().from(Person.class).where(Person_Table.id.eq(person.getId())));
        database.executeTransaction(db -> person.delete());
    }
}
