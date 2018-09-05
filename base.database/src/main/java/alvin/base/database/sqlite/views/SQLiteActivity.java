package alvin.adv.database.sqlite.views;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import alvin.adv.database.R;
import alvin.adv.database.common.domain.models.Gender;
import alvin.adv.database.common.domain.models.IPerson;
import alvin.adv.database.common.views.BaseActivity;
import alvin.adv.database.sqlite.domain.SQLite;
import alvin.adv.database.sqlite.domain.models.Person;
import alvin.adv.database.sqlite.domain.repositories.PersonSQLiteRepository;

public class SQLiteActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.sqlite_activity;
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
