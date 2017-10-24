package alvin.database.dbflow.repositories;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import alvin.database.dbflow.models.Person;
import alvin.database.dbflow.models.Person_Table;
import alvin.database.models.Gender;

public class PersonDBFlowRepository {
    private final DatabaseDefinition database;

    public PersonDBFlowRepository(DatabaseDefinition database) {
        this.database = database;
    }

    public List<Person> findByGender(Gender gender) {
        return SQLite.select().from(Person.class).where(Person_Table.gender.eq(gender)).queryList();
    }

    public void save(Person person) {
        database.executeTransaction(db -> person.save());
    }
}
