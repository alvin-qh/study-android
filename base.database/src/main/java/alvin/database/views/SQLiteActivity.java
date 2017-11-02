package alvin.database.views;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import alvin.database.R;
import alvin.database.models.Gender;
import alvin.database.models.IPerson;
import alvin.database.sqlite.SQLite;
import alvin.database.sqlite.models.Person;
import alvin.database.sqlite.repositories.PersonSQLiteRepository;

public class SQLiteActivity extends FrameActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sqlite;
    }

    @Override
    protected int getTitleId() {
        return R.string.title_sqlite;
    }

    @Override
    public void savePerson(String name, Gender gender, LocalDate birthday) {
        Person person = new Person(name, gender, birthday);
        try (SQLite sqlite = SQLite.createSQLiteDB(this)) {
            PersonSQLiteRepository repository = new PersonSQLiteRepository(sqlite.getWritable());
            repository.create(person);
        }
    }

    @Override
    public List<IPerson> getPersons(Gender gender) {
        try (SQLite sqlite = SQLite.createSQLiteDB(this)) {
            PersonSQLiteRepository repository = new PersonSQLiteRepository(sqlite.getReadable());
            return Collections.unmodifiableList(repository.findByGender(gender));
        }
    }

    @Override
    public void updatePerson(int id, String name, Gender gender, LocalDate birthday) {
        Person person = new Person(id, name, gender, birthday);
        try (SQLite sqlite = SQLite.createSQLiteDB(this)) {
            PersonSQLiteRepository repository = new PersonSQLiteRepository(sqlite.getReadable());
            repository.update(person);
        }
    }

    @Override
    public void deletePerson(int id) {
        try (SQLite sqlite = SQLite.createSQLiteDB(this)) {
            PersonSQLiteRepository repository = new PersonSQLiteRepository(sqlite.getReadable());
            repository.delete(id);
        }
    }
}
